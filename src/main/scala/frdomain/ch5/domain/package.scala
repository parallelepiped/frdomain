package frdomain.ch5
package domain

import scalaz._

package object service {
  type Valid[A] = NonEmptyList[String] \/ A
}
