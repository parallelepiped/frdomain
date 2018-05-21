package frdomain.ch5
package domain
package service

import frdomain.ch5.domain.repository.AccountRepository
import scalaz._


trait ReportingService[Amount] {
  type ReportOperation[A] = Kleisli[Valid, AccountRepository, A]

  def balanceByAccount: ReportOperation[Seq[(String, Amount)]]
} 
