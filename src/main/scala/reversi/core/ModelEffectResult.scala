package reversi.core

sealed trait ModelEffectResult[+A] {
  def map[B](fn: A => B): ModelEffectResult[B]
}

object ModelEffectResult {
  case object NoChange extends ModelEffectResult[Nothing] {
    def map[B](fn: Nothing => B): ModelEffectResult[B] = this
  }

  case class Changed[A](result: A) extends ModelEffectResult[A] {
    def map[B](fn: A => B): ModelEffectResult[B] = Changed(fn(result))
  }
}
