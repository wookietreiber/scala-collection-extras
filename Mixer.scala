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
import scala.util.Random

trait Mixer[M[_]] {
  def shuffle[A](xs: M[A]): M[A]
  def choose[A](xs: M[A], n: Int): M[A]
  def choosePair[A](xs: M[A]): Pair[A,A]
}

trait MixerLow0 {
  implicit def GenTraversableMixer[CC[X] <: GenTraversable[X]](implicit sorter: Sorter[CC]): Mixer[CC] = new Mixer[CC] {
    override def shuffle[A](xs: CC[A]): CC[A] = {
      val withRandom: CC[(A,Long)] = xs.map(x ⇒ x → Random.nextLong).asInstanceOf[CC[(A,Long)]]
      val shuffled = sorter.sortBy(withRandom) { _._2 } map { _._1 }
      shuffled.asInstanceOf[CC[A]]
    }
    override def choose[A](xs: CC[A], n: Int): CC[A] = shuffle(xs).take(n).asInstanceOf[CC[A]]
    override def choosePair[A](xs: CC[A]): Pair[A,A] = {
      val two = choose(xs, 2).toIndexedSeq
      Pair(two(0), two(1))
    }
  }
}

trait MixerLow extends MixerLow0 {
  implicit def SeqMixer[CC[X] <: Seq[X]]: Mixer[CC] = new Mixer[CC] {
    override def shuffle[A](xs: CC[A]): CC[A] = {
      val shuffled = xs.view map { x ⇒ x → Random.nextLong } sortBy { _._2 } map { _._1 }
      shuffled.force.asInstanceOf[CC[A]]
    }
    override def choose[A](xs: CC[A], n: Int): CC[A] = shuffle(xs).take(n).asInstanceOf[CC[A]]
    override def choosePair[A](xs: CC[A]): Pair[A,A] = {
      val two = choose(xs, 2).toIndexedSeq
      Pair(two(0), two(1))
    }
  }
}

object Mixer extends MixerLow
