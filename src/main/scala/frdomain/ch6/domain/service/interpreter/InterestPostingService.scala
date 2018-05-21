package frdomain.ch6
package domain
package service
package interpreter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

import frdomain.ch6.domain.model.Account
import frdomain.ch6.domain.model.common._
import scalaz.Kleisli._
import scalaz.Scalaz._
import scalaz._

class InterestPostingServiceInterpreter extends InterestPostingService[Account, Amount] {
  def computeInterest: Kleisli[Valid, Account, Amount] = kleisli[Valid, Account, Amount] { account: Account =>
    EitherT {
      Future {
        if (account.dateOfClose isDefined) NonEmptyList(s"Account ${account.no} is closed").left
        else Account.rate(account).map { r =>
          val a = account.balance.amount
          a + a * r
        }.getOrElse(BigDecimal(0)).right
      }
    }
  }

  def computeTax: Kleisli[Valid, Amount, Amount] = kleisli[Valid, Amount, Amount] { amount: Amount =>
    EitherT {
      Future {
        (amount * 0.1).right
      }
    }
  }
}

object InterestPostingService extends InterestPostingServiceInterpreter

