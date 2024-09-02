package reversi.core

import reversi.core.ModelEffectResult.*

trait ModelEffect[A] {
  def run(input: A): ModelEffectResult[A]

  def andThen(next: ModelEffect[A]): ModelEffect[A] = ModelEffect.composite(this, next)

  def convert[B](to: A => B, from: B => A): ModelEffect[B] = ModelEffect.Converted(this, to, from)
}

object ModelEffect {
  def apply[A](fn: A => ModelEffectResult[A]): ModelEffect[A] =
    FromFunction(fn)

  def noEffect[A]: ModelEffect[A] = NoEffect()

  def composite[A](effects: ModelEffect[A]*): ModelEffect[A] = {
    val components = effects.foldLeft(Vector.empty[ModelEffect[A]]) {
      case (acc, effect) => effect match {
        case NoEffect() => acc
        case Composite(cs) => acc ++ cs
        case other => acc :+ other
      }
    }
    if components.isEmpty then NoEffect()
    else if components.size == 1 then components.head
    else Composite(components)
  }

  private case class NoEffect[A]() extends ModelEffect[A] {
    def run(input: A): ModelEffectResult[A] = NoChange

    override def andThen(next: ModelEffect[A]): ModelEffect[A] = next
  }

  private case class FromFunction[A](fn: A => ModelEffectResult[A]) extends ModelEffect[A] {
    def run(input: A): ModelEffectResult[A] = fn(input)
  }

  private case class Composite[A](components: Vector[ModelEffect[A]]) extends ModelEffect[A] {
    def run(input: A): ModelEffectResult[A] = {
      var changed = false
      var result = input
      components.foreach { handler =>
        handler.run(result) match {
          case NoChange => ()
          case Changed(newResult) =>
            result = newResult
            changed = true
        }
      }
      if changed then Changed(result) else NoChange
    }

    override def andThen(next: ModelEffect[A]): ModelEffect[A] = {
      next match {
        case Composite(cs) => Composite(components ++ cs)
        case NoEffect() => this
        case other => Composite(components :+ other)
      }
    }
  }

  private case class Converted[A, B](underlying: ModelEffect[A], to: A => B, from: B => A) extends ModelEffect[B] {
    def run(input: B): ModelEffectResult[B] =
      underlying.run(from(input)).map(to)
  }
}
