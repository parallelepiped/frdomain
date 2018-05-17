package frdomain.ch5
package domain
package app

import scalaz._
import Scalaz._

import repository.interpreter.AccountRepositoryInMemory
import model.common._
import model.{ Account, Balance }
import Account._

object App2 {
  import AccountRepositoryInMemory._

  val account: Account = checkingAccount("a-123", "debasish ghosh", today.some, None, Balance()).toOption.get

  val c: NonEmptyList[String] \/ Balance = for {
    b <- updateBalance(account, 10000)
    c <- store(b)
    d <- balance(c.no)
  } yield d
}
