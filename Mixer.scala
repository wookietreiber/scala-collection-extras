package scalax.util

import language.higherKinds

import scala.annotation.tailrec
import scala.collection.parallel.immutable.ParVector
import scala.util.Random._

trait Mixer[M[_]] extends Sorter[M] {
  def shuffle[A](xs: M[A]): M[A]
}

object Mixer {
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

    override def sort[A:Ordering](xs: ParVector[A]): ParVector[A] = {
      xs.aggregate(xs.companion.empty[A])(
        (xs,y) ⇒ {
          val (ps,ss) = xs.partition(implicitly[Ordering[A]].lt(_,y))
          ps ++ xs.companion(y) ++ ss
        },
        mergeAll
      )
    }

    override def shuffle[A](xs: ParVector[A]): ParVector[A] =
      sortBy(xs) { _ ⇒ nextLong }
  }
}
