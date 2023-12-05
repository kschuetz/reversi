package reversi.ui.layout

opaque type Pixels = Int

object Pixels {
  def apply(value: Int): Pixels = value

  extension (self: Pixels) {
    def value: Int = self

    def +(rhs: Pixels): Pixels = value + rhs

    def *(rhs: Int): Pixels = value * rhs

    def /(rhs: Int): Pixels = value / rhs

    def >=(rhs: Pixels): Boolean = value >= rhs

    def toSvgString: String = s"${value}px"
  }
}
