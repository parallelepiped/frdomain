package frdomain.ch6
package streams

import java.util.{Calendar, Date}

object common {
  type Amount = BigDecimal

  def today: Date = Calendar.getInstance.getTime
}

import frdomain.ch6.streams.common._

case class Balance(amount: Amount = 0)

case class Account (no: String, name: String, dateOfOpen: Option[Date], dateOfClose: Option[Date] = None, 
  balance: Balance = Balance()) 
