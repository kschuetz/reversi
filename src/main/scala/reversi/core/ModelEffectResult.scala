package reversi.core

sealed trait ModelEffectResult[+A] {
  def map[B](fn: A => B): ModelEffectResult[B]
}

object ModelEffectResult {
  def noChange[A]: ModelEffectResult[A] = NoChange

  def changed[A](newValue: A): ModelEffectResult[A] = Changed(newValue)

  case object NoChange extends ModelEffectResult[Nothing] {
    def map[B](fn: Nothing => B): ModelEffectResult[B] = this
  }

  case class Changed[A](newValue: A) extends ModelEffectResult[A] {
    def map[B](fn: A => B): ModelEffectResult[B] = Changed(fn(newValue))
  }
}
