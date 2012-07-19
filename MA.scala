package scalax.util

import language.higherKinds

trait MA[M[_],A] {
  val value: M[A]

  def shuffle(implicit mixer: Mixer[M]): M[A] =
    mixer.shuffle(value)

  def sortWith(lt: (A,A) ⇒ Boolean)(implicit sorter: Sorter[M]): M[A] =
    sorted(Ordering fromLessThan lt, sorter)

  def sortBy[B](f: A ⇒ B)(implicit ord: Ordering[B], sorter: Sorter[M]): M[A] =
    sorted(ord on f, sorter)

  def sorted(implicit ord: Ordering[A], sorter: Sorter[M]): M[A] =
    sorter.sort(value)
}
