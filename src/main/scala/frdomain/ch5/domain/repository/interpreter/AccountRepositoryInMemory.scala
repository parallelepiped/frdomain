package frdomain.ch5
package domain
package repository
package interpreter

import scala.collection.mutable.{Map => MMap}

import frdomain.ch5.domain.model.Account
import scalaz.Scalaz._
import scalaz._

import java.util.Date

trait AccountRepositoryInMemory extends AccountRepository {
  lazy val repo = MMap.empty[String, Account]

  def query(no: String): \/[NonEmptyList[String], Option[Account]] = repo.get(no).right
  def store(a: Account): \/[NonEmptyList[String], Account] = {
    repo += ((a.no, a))
    a.right
  }
  def query(openedOn: Date): \/[NonEmptyList[String], Seq[Account]] = repo.values.filter(_.dateOfOpen == openedOn).toSeq.right
  def all: \/[NonEmptyList[String], Seq[Account]] = repo.values.toSeq.right
}

object AccountRepositoryInMemory extends AccountRepositoryInMemory

