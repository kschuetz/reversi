package reversi.core

import reversi.ui.models.PieceTransforms

case class GameState(board: BoardState,
                     beginTurnEvaluation: BeginTurnEvaluation,
                     readyForInput: Boolean,
                     pieceTransforms: PieceTransforms) {
  def turnToPlay: Option[Color] = beginTurnEvaluation match {
    case ip: BeginTurnEvaluation.InProgress => Some(ip.turnToPlay)
    case _ => None
  }

  def legalMoves: Set[SquareIndex] = beginTurnEvaluation.legalMoves
}
