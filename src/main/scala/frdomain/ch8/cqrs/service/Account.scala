package frdomain.ch8
package cqrs
package service

import frdomain.ch8.cqrs.lib.Aggregate
import org.joda.time.DateTime
import scalaz._

object common {
  type Amount = BigDecimal
  type Error = String

  val today: DateTime = DateTime.now()
}

import frdomain.ch8.cqrs.service.common._

case class Balance(amount: Amount = 0)

case class Account(no: String, name: String, dateOfOpening: DateTime = today, dateOfClosing: Option[DateTime] = None, 
  balance: Balance = Balance(0)) extends Aggregate {
  def id: String = no
  def isClosed: Boolean = dateOfClosing.isDefined
}

object Account {
  implicit val showAccount: Show[Account] = Show.shows { a: Account => a.toString }
}



