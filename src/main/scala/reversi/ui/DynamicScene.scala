package reversi.ui

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.children
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGGElement}
import reversi.core.{BoardState, Color, SquareIndex}
import reversi.ui.models.{PieceState, PieceTransforms}
import reversi.ui.piece.PhysicalPiece

final class DynamicScene(PhysicalPiece: PhysicalPiece) {

  def apply($boardState: Signal[BoardState],
            $pieceTransforms: Signal[PieceTransforms]): ReactiveSvgElement[SVGElement] = {
    val $piecesMap = $boardState.combineWithFn($pieceTransforms)(buildPiecesList)
    val $pieces = $piecesMap.split(_.squareIndex)(renderPiece)
    g(children <-- $pieces)
  }

  private def buildPiecesList(boardState: BoardState,
                              pieceTransforms: PieceTransforms): Vector[PieceNode] =
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

  private def makePieceNode(squareIndex: SquareIndex,
                            color: Color,
                            ghost: Boolean,
                            pieceTransforms: PieceTransforms): PieceNode = {
    val flipPosition = pieceTransforms.getFlipPosition(squareIndex)
    val pieceState = PieceState(color = color, xpos = 0, ypos = 0, flipPosition = flipPosition,
      ghost = ghost)
    PieceNode(squareIndex, pieceState)
  }

  private def renderPiece(squareIndex: SquareIndex,
                          initial: PieceNode,
                          signal: Signal[PieceNode]): ReactiveSvgElement[SVGElement] =
    PhysicalPiece(signal.map(_.pieceState))

  private case class PieceNode(squareIndex: SquareIndex,
                               pieceState: PieceState)

}
