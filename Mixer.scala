package scalax.util

import scala.annotation.tailrec
import scala.collection.GenTraversable
import scala.collection.generic.CanBuildFrom
import scala.collection.parallel.immutable.ParVector
import scala.util.Random._

trait Mixer[M[_]] extends Sorter[M] {
  def shuffle[A](xs: M[A]): M[A]
}

trait MixerLow {
  implicit def GenTraversableMixer[CC[X] <: GenTraversable[X]]: Mixer[CC] = new Mixer[CC] {
    override def merge[A:Ordering](xs: CC[A], y: A) = {
      val (ps,ss) = xs.partition(implicitly[Ordering[A]].lt(_,y))
      val builder = xs.genericBuilder[A]
      builder ++= ps.seq
      builder  += y
      builder ++= ss.seq
      builder.result.asInstanceOf[CC[A]]
    }

    @tailrec
    override def mergeAll[A:Ordering](xs: CC[A], ys: CC[A]) =
      if (ys.isEmpty) xs else mergeAll(merge(xs,ys.head), ys.tail.asInstanceOf[CC[A]])

    override def sort[A:Ordering](xs: CC[A]) =
      xs.aggregate(xs.genericBuilder[A].result.asInstanceOf[CC[A]])(merge, mergeAll)

    override def shuffle[A](xs: CC[A]): CC[A] =
      sortBy(xs) { _ ⇒ nextLong }
  }
}

object Mixer extends MixerLow {
  implicit val ParVectorMixer: Mixer[ParVector] = new Mixer[ParVector] {
    override def merge[A:Ordering](xs: ParVector[A], y: A): ParVector[A] = {
      val (ps,ss) = xs.partition(implicitly[Ordering[A]].lt(_,y))
      val builder = xs.genericBuilder[A]
      builder ++= ps.seq
      builder  += y
      builder ++= ss.seq
      builder.result
    }

    @tailrec
    override def mergeAll[A:Ordering](xs: ParVector[A], ys: ParVector[A]): ParVector[A] =
      if (ys.isEmpty) xs else mergeAll(merge(xs,ys.head), ys.tail)

    override def sort[A:Ordering](xs: ParVector[A]): ParVector[A] =
      xs.aggregate(xs.companion.empty[A])(merge, mergeAll)

    override def shuffle[A](xs: ParVector[A]): ParVector[A] =
      sortBy(xs) { _ ⇒ nextLong }
  }
}
