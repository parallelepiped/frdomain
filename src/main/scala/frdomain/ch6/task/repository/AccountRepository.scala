package frdomain.ch6
package task
package repository

import frdomain.ch6.task.model._
import scalaz._

import java.util.Date

trait AccountRepository {
  def query(no: String): \/[NonEmptyList[String], Option[Account]]
  def store(a: Account): \/[NonEmptyList[String], Account]
  def query(openedOn: Date): \/[NonEmptyList[String], Seq[Account]]
  def all: \/[NonEmptyList[String], Seq[Account]]

  def getCurrencyBalance(no: String, asOf: Date): String \/ Seq[Balance]
  def getEquityBalance(no: String, asOf: Date): String \/ Seq[Balance]
  def getFixedIncomeBalance(no: String, asOf: Date): String \/ Seq[Balance]
}

