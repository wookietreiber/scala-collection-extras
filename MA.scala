/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  This file is part of the project 'scala-collection-extras'.                                  *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  This project is free software. It comes without any warranty, to the extent permitted by     *
 *  applicable law. You can redistribute it and/or modify it under the terms of the Do What The  *
 *  Fuck You Want To Public License, Version 2, as published by Sam Hocevar.                     *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  See http://sam.zoy.org/wtfpl/COPYING for more details.                                       *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


package scalay.collection

trait MA[M[_],A] {
  val value: M[A]

  // -----------------------------------------------------------------------------------------------
  // sorting
  // -----------------------------------------------------------------------------------------------

  def sortWith(lt: (A,A) ⇒ Boolean)(implicit sorter: Sorter[M]): M[A] =
    sorted(Ordering fromLessThan lt, sorter)

  def sortBy[B](f: A ⇒ B)(implicit ord: Ordering[B], sorter: Sorter[M]): M[A] =
    sorted(ord on f, sorter)

  def sorted(implicit ord: Ordering[A], sorter: Sorter[M]): M[A] =
    sorter.sort(value)

  // -----------------------------------------------------------------------------------------------
  // shuffling
  // -----------------------------------------------------------------------------------------------

  def shuffle(implicit mixer: Mixer[M]): M[A] =
    mixer.shuffle(value)

  def choose(n: Int)(implicit mixer: Mixer[M]): M[A] =
    mixer.choose(value, n)

  def choosePair(implicit mixer: Mixer[M]): Pair[A,A] =
    mixer.choosePair(value)

  def average(implicit stats: Statistics[M], num: Numeric[A]): Double =
    stats.average(value)

  def averageBy[B](f: A ⇒ B)(implicit stats: Statistics[M], num: Numeric[B]): Double =
    stats.averageBy(value)(f)

}
