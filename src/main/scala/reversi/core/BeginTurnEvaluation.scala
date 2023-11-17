package reversi.core

sealed trait BeginTurnEvaluation {
  def powerBalance: Power
}

object BeginTurnEvaluation {
  case class InProgress(turnToPlay: Color,
                        legalMoves: Set[SquareIndex],
                        powerBalance: Power) extends BeginTurnEvaluation

  case class Win(winner: Color) extends BeginTurnEvaluation {
    def powerBalance: Power = winner match {
      case Color.Dark => Power.DarkWin
      case Color.Light => Power.LightWin
    }
  }

  case object Draw extends BeginTurnEvaluation {
    def powerBalance: Power = Power.Even
  }
}
