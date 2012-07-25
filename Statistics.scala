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

import scala.collection.GenTraversable

trait Statistics[M[_]] {
  def average[A:Numeric](xs: M[A]): Double
  def averageBy[A,B:Numeric](xs: M[A])(f: A ⇒ B): Double
}

trait StatisticsLow {
  implicit def GenTraversableStatistics[CC[X] <: GenTraversable[X]]: Statistics[CC] = new Statistics[CC] {
    def average[A](xs: CC[A])(implicit num: Numeric[A]): Double = {
      import num._
      xs.aggregate(zero)(_ + _, _ + _).toDouble / xs.size
    }

    def averageBy[A,B](xs: CC[A])(f: A ⇒ B)(implicit num: Numeric[B]): Double = {
      import num._
      xs.aggregate(zero)(_ + f(_), _ + _).toDouble / xs.size
    }
  }
}

object Statistics extends StatisticsLow
