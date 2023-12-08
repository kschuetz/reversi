package reversi.ui.models

/**
 * Number from 0 to 1 (inclusive)
 */
opaque type Fraction = Double

object Fraction {
  inline def Zero: Fraction = 0d

  inline def One: Fraction = 1d

  def apply(value: Double): Fraction =
    if value < 0 then 0d
    else if value > 1 then 1d
    else value

  extension (self: Fraction) {
    def value: Double = self
  }
}
