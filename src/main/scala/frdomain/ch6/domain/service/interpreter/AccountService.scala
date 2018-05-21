package frdomain.ch6
package domain
package service
package interpreter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

import frdomain.ch6.domain.model.common._
import frdomain.ch6.domain.model.{Account, Balance}
import frdomain.ch6.domain.repository.AccountRepository
import scalaz.Kleisli._
import scalaz.Scalaz._
import scalaz._

import java.util.Date

class AccountServiceInterpreter extends AccountService[Account, Amount, Balance] {

  def open(no: String, 
           name: String, 
           rate: Option[BigDecimal],
           openingDate: Option[Date],
           accountType: AccountType): AccountOperation[Account] = kleisli[Valid, AccountRepository, Account] { repo: AccountRepository =>

    EitherT {
      Future {
        repo.query(no) match {
          case \/-(Some(a)) => NonEmptyList(s"Already existing account with no $no").left[Account]
          case \/-(None)    => accountType match {
            case Checking => Account.checkingAccount(no, name, openingDate, None, Balance()).flatMap(repo.store)
            case Savings  => rate map { r =>
              Account.savingsAccount(no, name, r, openingDate, None, Balance()).flatMap(repo.store)
            } getOrElse {
              NonEmptyList(s"Rate needs to be given for savings account").left[Account]
            }
          }
          case a @ -\/(_) => a
        }
      }
    }
  }

  def close(no: String, closeDate: Option[Date]): AccountOperation[Account] = kleisli[Valid, AccountRepository, Account] { repo: AccountRepository =>
    EitherT {
      Future {
        repo.query(no) match {
          case \/-(None) => NonEmptyList(s"Account $no does not exist").left[Account]
          case \/-(Some(a)) => 
            val cd = closeDate.getOrElse(today)
            Account.close(a, cd).flatMap(repo.store)
          case a @ -\/(_) => a
        }
      }
    }
  }

  def debit(no: String, amount: Amount): AccountOperation[Account] = up(no, amount, D)
  def credit(no: String, amount: Amount): AccountOperation[Account] = up(no, amount, C)

  private trait DC
  private case object D extends DC
  private case object C extends DC

  private def up(no: String, amount: Amount, dc: DC): AccountOperation[Account] = kleisli[Valid, AccountRepository, Account] { repo: AccountRepository =>
    EitherT {
      Future {
        repo.query(no) match {
          case \/-(None) => NonEmptyList(s"Account $no does not exist").left[Account]
          case \/-(Some(a)) => dc match {
            case D => Account.updateBalance(a, -amount).flatMap(repo.store) 
            case C => Account.updateBalance(a, amount).flatMap(repo.store) 
          }
          case a @ -\/(_) => a
        }
      }
    }
  }

  def balance(no: String): AccountOperation[Balance] = kleisli[Valid, AccountRepository, Balance] { repo: AccountRepository =>
      EitherT {
        Future { repo.balance(no) }
      }
    }
}

object AccountService extends AccountServiceInterpreter
