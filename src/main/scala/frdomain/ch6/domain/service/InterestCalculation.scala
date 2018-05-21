package frdomain.ch6
package domain
package service

import scalaz._

trait InterestCalculation[Account, Amount] {
  def computeInterest: Kleisli[Valid, Account, Amount]
}
