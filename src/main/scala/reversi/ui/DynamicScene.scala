package reversi.ui

import com.raquo.airstream.core.Signal
import com.raquo.airstream.eventbus.WriteBus
import com.raquo.laminar.api.L.children
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGGElement}
import reversi.core.{Color, GameState, SquareIndex}
import reversi.ui.board.{BoardOverlayButtons, PhysicalBoard}
import reversi.ui.models.{PieceState, PieceTransforms, SquareInteraction}
import reversi.ui.piece.PhysicalPiece

final class DynamicScene(PhysicalPiece: PhysicalPiece,
                         BoardOverlayButtons: BoardOverlayButtons) {

  def apply($gameState: Signal[GameState],
            $pieceTransforms: Signal[PieceTransforms],
            squareInteractions: WriteBus[SquareInteraction]): ReactiveSvgElement[SVGElement] = {
    val $piecesMap = $gameState.combineWithFn($pieceTransforms)(buildPiecesList)
    val $pieces = $piecesMap.split(_.squareIndex)(renderPiece)
    val $welcomeToClick = $gameState.map(_.legalMoves)
    g(children <-- $pieces, BoardOverlayButtons(squareInteractions, $welcomeToClick))
  }

  private def buildPiecesList(gameState: GameState,
                              pieceTransforms: PieceTransforms): Vector[PieceNode] = {
    val boardState = gameState.board
    SquareIndex.All.foldLeft(Vector.empty[PieceNode]) {
      case (acc, idx) =>
        boardState.get(idx) match {
          case Some(color) => acc :+ makePieceNode(idx, color, false, pieceTransforms)
          case None =>
            pieceTransforms.ghostPieces.get(idx) match {
              case Some(color) => acc :+ makePieceNode(idx, color, false, pieceTransforms)
              case None => acc
            }
        }
    }
  }

  private def makePieceNode(squareIndex: SquareIndex,
                            color: Color,
                            ghost: Boolean,
                            pieceTransforms: PieceTransforms): PieceNode = {
    val flipPosition = pieceTransforms.getFlipPosition(squareIndex)
    val pieceState = PieceState(color = color, position = PhysicalBoard.centerOfSquare(squareIndex),
      flipPosition = flipPosition, ghost = ghost)
    PieceNode(squareIndex, pieceState)
  }

  private def renderPiece(squareIndex: SquareIndex,
                          initial: PieceNode,
                          signal: Signal[PieceNode]): ReactiveSvgElement[SVGElement] =
    PhysicalPiece(signal.map(_.pieceState))

  private case class PieceNode(squareIndex: SquareIndex,
                               pieceState: PieceState)

}
