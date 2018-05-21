package frdomain.ch6
package task
package service
package interpreter

import frdomain.ch6.task.model._
import frdomain.ch6.task.repository.AccountRepository
import scalaz.Kleisli._
import scalaz._
import scalaz.concurrent.Task

import java.util.Date

class PortfolioServiceInterpreter extends PortfolioService {
  def getCurrencyPortfolio(no: String, asOf: Date): PFOperation[Balance] = kleisli[Task, AccountRepository, Seq[Balance]] { repo: AccountRepository =>

    Task {
      repo.getCurrencyBalance(no, asOf) match {
        case \/-(b) => b
        case -\/(_) => throw new Exception(s"Failed to fetch currency balance")
      }
    }
  }

  def getEquityPortfolio(no: String, asOf: Date): PFOperation[Balance] = kleisli[Task, AccountRepository, Seq[Balance]] { repo: AccountRepository =>

    Task {
      repo.getEquityBalance(no, asOf) match {
        case \/-(b) => b
        case -\/(_) => throw new Exception(s"Failed to fetch equity balance")
      }
    }
  }

  def getFixedIncomePortfolio(no: String, asOf: Date): PFOperation[Balance] = kleisli[Task, AccountRepository, Seq[Balance]] { repo: AccountRepository =>

    Task {
      repo.getFixedIncomeBalance(no, asOf) match {
        case \/-(b) => b
        case -\/(_) => throw new Exception(s"Failed to fetch fixed income balance")
      }
    }
  }
}

object PortfolioServiceInterpreter extends PortfolioServiceInterpreter
