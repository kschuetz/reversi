package reversi.animation

opaque type Millis = Double

object Millis {
  def apply(value: Double): Millis = value

  extension (value: Millis) {
    def toDouble: Double = value

    def /(rhs: Millis): Double = value / rhs

    def >(rhs: Millis): Boolean = value > rhs

    def <(rhs: Millis): Boolean = value < rhs

    def >=(rhs: Millis): Boolean = value >= rhs

    def <=(rhs: Millis): Boolean = value <= rhs
  }
}

opaque type Instant = Double

object Instant {
  def apply(value: Double): Instant = value

  extension (value: Instant) {
    def toDouble: Double = value

    def +(rhs: Millis): Instant = Instant(value + rhs)

    def -(rhs: Instant): Millis = Millis(value - rhs)

    def >(rhs: Instant): Boolean = value > rhs

    def <(rhs: Instant): Boolean = value < rhs

    def >=(rhs: Instant): Boolean = value >= rhs

    def <=(rhs: Instant): Boolean = value <= rhs
  }
}


