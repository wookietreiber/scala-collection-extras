package scalax.util

trait Merger[M[_]] {
  def merge[A:Ordering](xs: M[A], y: A): M[A]
  def mergeAll[A:Ordering](xs: M[A], ys: M[A]): M[A]
}

trait MergerLow {
  implicit def byMixer[M[_]:Mixer]: Merger[M] = implicitly[Mixer[M]]
}

object Merger extends MergerLow
