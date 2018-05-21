package frdomain.ch5
package free

import scalaz._

import java.util.{Calendar, Date}

object common {
  type Amount = BigDecimal

  val today: Date = Calendar.getInstance.getTime
}

import frdomain.ch5.free.common._

case class Balance(amount: Amount = 0)

case class Account(no: String, name: String, dateOfOpening: Date = today, dateOfClosing: Option[Date] = None, 
  balance: Balance = Balance(0))

object Account {
  implicit val showAccount: Show[Account] = Show.shows { a: Account => a.toString }
}


