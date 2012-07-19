package scalax.util

import language.higherKinds

trait Sorter[M[_]] extends Merger[M] {
  def sortWith[A](xs: M[A])(lt: (A,A) ⇒ Boolean): M[A] = sort(xs)(Ordering fromLessThan lt)
  def sortBy[A,B:Ordering](xs: M[A])(f: A ⇒ B): M[A] = sort(xs)(implicitly[Ordering[B]] on f)
  def sort[A:Ordering](xs: M[A]): M[A]
}

trait SorterLow {
  implicit def byMixer[M[_]:Mixer]: Sorter[M] = implicitly[Mixer[M]]
}

object Sorter extends SorterLow
