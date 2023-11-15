package reversi.core

opaque type Power = Double

object Power {

  val Even: Power = 0
  val DarkWin: Power = -1
  val LightWin: Power = 1

  def apply(value: Double): Power =
    if value < DarkWin then DarkWin
    else if value > LightWin then LightWin
    else value

  extension (self: Power) {
    def value: Double = self
  }
}
