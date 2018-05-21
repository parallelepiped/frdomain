package frdomain.ch6
package task
package app

import frdomain.ch6.task.model._
import frdomain.ch6.task.model.common._
import frdomain.ch6.task.repository.interpreter.AccountRepositoryInMemory
import frdomain.ch6.task.service.interpreter.PortfolioServiceInterpreter
import scalaz.Kleisli._
import scalaz.concurrent.Task

import java.util.Date

object Main {

  import PortfolioServiceInterpreter._

  val accountNo = "a-123"
  val asOf: Date = today

  val ccyPF: Task[Seq[Balance]] = getCurrencyPortfolio(accountNo, asOf)(AccountRepositoryInMemory)
  val eqtPF: Task[Seq[Balance]] = getEquityPortfolio(accountNo, asOf)(AccountRepositoryInMemory)
  val fixPF: Task[Seq[Balance]] = getFixedIncomePortfolio(accountNo, asOf)(AccountRepositoryInMemory)

  val r: Task[List[Seq[Balance]]] = Task.gatherUnordered(Seq(ccyPF, eqtPF, fixPF))
  val portfolio = CustomerPortfolio(accountNo, asOf, r.unsafePerformSync.foldLeft(List.empty[Balance])(_ ++ _))
}

