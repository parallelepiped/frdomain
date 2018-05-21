package frdomain.ch6
package future
package service
package interpreter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

import frdomain.ch6.future.model._
import frdomain.ch6.future.repository.AccountRepository
import scalaz.Kleisli._
import scalaz._

import java.util.Date

class PortfolioServiceInterpreter extends PortfolioService {
  def getCurrencyPortfolio(no: String, asOf: Date): PFOperation[Balance] = kleisli[Future, AccountRepository, Seq[Balance]] { repo: AccountRepository =>

    Future {
      repo.getCurrencyBalance(no, asOf) match {
        case \/-(b) => b
        case -\/(_) => throw new Exception(s"Failed to fetch currency balance")
      }
    }
  }

  def getEquityPortfolio(no: String, asOf: Date): PFOperation[Balance] = kleisli[Future, AccountRepository, Seq[Balance]] { repo: AccountRepository =>

    Future {
      repo.getEquityBalance(no, asOf) match {
        case \/-(b) => b
        case -\/(_) => throw new Exception(s"Failed to fetch equity balance")
      }
    }
  }

  def getFixedIncomePortfolio(no: String, asOf: Date): PFOperation[Balance] = kleisli[Future, AccountRepository, Seq[Balance]] { repo: AccountRepository =>

    Future {
      repo.getFixedIncomeBalance(no, asOf) match {
        case \/-(b) => b
        case -\/(_) => throw new Exception(s"Failed to fetch fixed income balance")
      }
    }
  }
}

object PortfolioService extends PortfolioServiceInterpreter
