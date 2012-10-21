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


package scalax.collection

import language.higherKinds

import scala.annotation.tailrec
import scala.collection.GenTraversable

trait Sorter[M[_]] {
  def sortWith[A](xs: M[A])(lt: (A,A) ⇒ Boolean): M[A] = sort(xs)(Ordering fromLessThan lt)
  def sortBy[A,B:Ordering](xs: M[A])(f: A ⇒ B): M[A] = sort(xs)(implicitly[Ordering[B]] on f)
  def sort[A:Ordering](xs: M[A]): M[A]
}

/* What we would need is a parallel collection that has the following properties:
 *
 * - cheap head/tail decomposition for mergeAll (like a list)
 * - cheap insert for merge (using buckets of some sort)
 *
trait SorterLow0 {
  implicit def GenTraversableSorter[CC[X] <: GenTraversable[X]]: Sorter[CC] = new Sorter[CC] {
    def merge[A:Ordering](xs: CC[A], y: A): CC[A] = {
      val (ps,ss) = xs.partition(implicitly[Ordering[A]].lt(_,y)) // could be insert as well
      val builder = xs.genericBuilder[A]
      builder ++= ps.seq
      builder  += y
      builder ++= ss.seq
      builder.result.asInstanceOf[CC[A]]
    }

    @tailrec
    def mergeAll[A:Ordering](xs: CC[A], ys: CC[A]): CC[A] =
      if (ys.isEmpty) xs else mergeAll(merge(xs,ys.head), ys.tail.asInstanceOf[CC[A]])

    override def sort[A:Ordering](xs: CC[A]): CC[A] =
      xs.aggregate(xs.genericBuilder[A].result.asInstanceOf[CC[A]])(merge, mergeAll)
  }
}
*/

trait SorterLow {
  implicit def SeqSorter[CC[X] <: scala.collection.Seq[X]]: Sorter[CC] = new Sorter[CC] {
    override def sort[A:Ordering](xs: CC[A]): CC[A] =
      xs.sorted(implicitly[Ordering[A]]).asInstanceOf[CC[A]]
  }
}

object Sorter extends SorterLow
