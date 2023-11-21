package reversi.ui.layout

opaque type Pixels = Int

object Pixels {
  def apply(value: Int): Pixels = value
}

extension (self: Pixels) {
  def value: Int = self

  def >=(rhs: Pixels): Boolean = self >= rhs
}
