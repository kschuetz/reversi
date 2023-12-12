package reversi.core

case class GameState(board: BoardState,
                     beginTurnEvaluation: BeginTurnEvaluation) {
  def turnToPlay: Option[Color] = beginTurnEvaluation match {
    case ip: BeginTurnEvaluation.InProgress => Some(ip.turnToPlay)
    case _ => None
  }

  def legalMoves: Set[SquareIndex] = beginTurnEvaluation.legalMoves
}
