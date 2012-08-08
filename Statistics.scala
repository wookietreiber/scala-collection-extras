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
  def arithmeticMean[A:Numeric](xs: M[A]): Double
  def arithmeticMeanBy[A,B:Numeric](xs: M[A])(f: A ⇒ B): Double

  def geometricMean[A:Numeric](xs: M[A]): Double
  def geometricMeanBy[A,B:Numeric](xs: M[A])(f: A ⇒ B): Double

  def harmonicMean[A:Integral](xs: M[A]): Double
  def harmonicMeanBy[A,B:Integral](xs: M[A])(f: A ⇒ B): Double
}

trait StatisticsLow {
  implicit def GenTraversableStatistics[CC[X] <: GenTraversable[X]]: Statistics[CC] = new Statistics[CC] {
    def arithmeticMean[A](xs: CC[A])(implicit num: Numeric[A]): Double = {
      import num._
      xs.aggregate(zero)(_ + _, _ + _).toDouble / xs.size
    }

    def arithmeticMeanBy[A,B](xs: CC[A])(f: A ⇒ B)(implicit num: Numeric[B]): Double = {
      import num._
      xs.aggregate(zero)(_ + f(_), _ + _).toDouble / xs.size
    }

    def geometricMean[A](xs: CC[A])(implicit num: Numeric[A]): Double = {
      import num._
      math.pow(xs.product.toDouble, 1. / xs.size)
    }

    def geometricMeanBy[A,B](xs: CC[A])(f: A ⇒ B)(implicit num: Numeric[B]): Double = {
      import num._
      val acc = xs.aggregate(one)(_ * f(_), _ * _).toDouble
      math.pow(acc, 1. / xs.size)
    }

    def harmonicMean[A](xs: CC[A])(implicit int: Integral[A]): Double = {
      import int._
      val acc = xs.aggregate(zero)(_ + one / _, _ + _).toDouble
      xs.size / acc
    }

    def harmonicMeanBy[A,B](xs: CC[A])(f: A ⇒ B)(implicit int: Integral[B]): Double = {
      import int._
      val acc = xs.aggregate(zero)(_ + one / f(_), _ + _).toDouble
      xs.size / acc
    }
  }
}

object Statistics extends StatisticsLow
