package frdomain.ch6
package future
package repository
package interpreter

import scala.collection.mutable.{Map => MMap}

import frdomain.ch6.future.model._
import scalaz.Scalaz._
import scalaz._

import java.util.Date

trait AccountRepositoryInMemory extends AccountRepository {
  private lazy val repo = MMap.empty[String, Account]
  private lazy val ccyBalanceRepo = MMap.empty[(String, Date), Seq[Balance]]
  private lazy val equityBalanceRepo = MMap.empty[(String, Date), Seq[Balance]]
  private lazy val fixedIncomeBalanceRepo = MMap.empty[(String, Date), Seq[Balance]]

  def query(no: String): \/[NonEmptyList[String], Option[Account]] = repo.get(no).right
  def store(a: Account): \/[NonEmptyList[String], Account] = {
    repo += ((a.no, a))
    a.right
  }
  def query(openedOn: Date): \/[NonEmptyList[String], Seq[Account]] = repo.values.filter(_.dateOfOpen == openedOn).toSeq.right
  def all: \/[NonEmptyList[String], Seq[Account]] = repo.values.toSeq.right

  def getCurrencyBalance(no: String, asOf: Date): String \/ Seq[Balance] = 
    ccyBalanceRepo.getOrElse((no, asOf), Seq.empty[Balance]).right

  def getEquityBalance(no: String, asOf: Date): String \/ Seq[Balance] = 
    equityBalanceRepo.getOrElse((no, asOf), Seq.empty[Balance]).right

  def getFixedIncomeBalance(no: String, asOf: Date): String \/ Seq[Balance] = 
    fixedIncomeBalanceRepo.getOrElse((no, asOf), Seq.empty[Balance]).right
}

object AccountRepositoryInMemory extends AccountRepositoryInMemory

