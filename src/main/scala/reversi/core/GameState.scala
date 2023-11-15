package reversi.core

sealed trait GameState {
  def powerBalance: Power
}

object GameState {
  case class InProgress(turnToPlay: Color,
                        legalMoves: Set[SquareIndex],
                        powerBalance: Power) extends GameState

  case class Win(winner: Color) extends GameState {
    def powerBalance: Power = winner match {
      case Color.Dark => Power.DarkWin
      case Color.Light => Power.LightWin
    }
  }

  case object Draw extends GameState {
    def powerBalance: Power = Power.Even
  }
}
