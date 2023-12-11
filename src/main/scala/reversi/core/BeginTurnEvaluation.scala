package reversi.core

sealed trait BeginTurnEvaluation {
  def powerBalance: Power

  def legalMoves: Set[SquareIndex]
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

    def legalMoves: Set[SquareIndex] = Set.empty
  }

  case object Draw extends BeginTurnEvaluation {
    def powerBalance: Power = Power.Even

    def legalMoves: Set[SquareIndex] = Set.empty
  }
}
