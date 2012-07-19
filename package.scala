package scalax

import language.higherKinds
import language.implicitConversions

package object util {
  implicit def toMA[M[_],A](ma: M[A]): MA[M,A] = new MA[M,A] {
    val value = ma
  }
}