package frdomain.ch6
package domain
package app

import frdomain.ch6.domain.model.common.Amount
import frdomain.ch6.domain.repository.AccountRepository
import frdomain.ch6.domain.repository.interpreter.AccountRepositoryInMemory
import frdomain.ch6.domain.service.interpreter.{AccountService, ReportingService}
import frdomain.ch6.domain.service.{Checking, Savings, Valid}
import scalaz.Kleisli
import scalaz.Kleisli._
import scalaz.Scalaz._

object App {

  import AccountService._
  import ReportingService._

  val opens: Kleisli[Valid, AccountRepository, Unit] =
    for {
      _ <- open("a1234", "a1name", None, None, Checking)
      _ <- open("a2345", "a2name", None, None, Checking)
      _ <- open("a3456", "a3name", BigDecimal(5.8).some, None, Savings)
      _ <- open("a4567", "a4name", None, None, Checking)
      _ <- open("a5678", "a5name", BigDecimal(2.3).some, None, Savings)
    } yield ()

  val credits: Kleisli[Valid, AccountRepository, Unit] =
    for {
      _ <- credit("a1234", 1000)
      _ <- credit("a2345", 2000)
      _ <- credit("a3456", 3000)
      _ <- credit("a4567", 4000)
    } yield ()

  val c: Kleisli[Valid, AccountRepository, Seq[(String, Amount)]] = for {
    _ <- opens
    _ <- credits
    a <- balanceByAccount
  } yield a

  val y: Valid[Seq[(String, Amount)]] = c(AccountRepositoryInMemory)
}
