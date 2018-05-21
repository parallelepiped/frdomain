package frdomain.ch6
package task
package service

import scala.language.higherKinds

import frdomain.ch6.task.model._
import frdomain.ch6.task.repository.AccountRepository
import scalaz._
import scalaz.concurrent.Task

import java.util.Date

trait PortfolioService {
  type PFOperation[A] = Kleisli[Task, AccountRepository, Seq[A]]

  def getCurrencyPortfolio(no: String, asOf: Date): PFOperation[Balance]
  def getEquityPortfolio(no: String, asOf: Date): PFOperation[Balance]
  def getFixedIncomePortfolio(no: String, asOf: Date): PFOperation[Balance]
}


