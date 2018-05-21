package frdomain.ch6
package domain
package service

import scalaz._

trait TaxCalculation[Amount] {
  def computeTax: Kleisli[Valid, Amount, Amount]
}
