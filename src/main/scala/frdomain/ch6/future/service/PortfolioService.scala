package frdomain.ch6
package future
package service

import scala.concurrent._
import scala.language.higherKinds

import frdomain.ch6.future.model._
import frdomain.ch6.future.repository.AccountRepository
import scalaz._

import java.util.Date

trait PortfolioService {
  type PFOperation[A] = Kleisli[Future, AccountRepository, Seq[A]]

  def getCurrencyPortfolio(no: String, asOf: Date): PFOperation[Balance]
  def getEquityPortfolio(no: String, asOf: Date): PFOperation[Balance]
  def getFixedIncomePortfolio(no: String, asOf: Date): PFOperation[Balance]
}


