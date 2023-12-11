package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.core.Color
import reversi.ui.models.{Fraction, PieceState, Point}
import reversi.ui.piece.PhysicalPiece

object PieceAvatar {
  private val FlipPosition = Fraction(0.07)
}

final class PieceAvatar(PhysicalPiece: PhysicalPiece) {

  import PieceAvatar.*

  def apply(color: Color,
            $position: Signal[Point],
            $isPlayerTurn: Signal[Boolean],
            $scale: Signal[Double]): ReactiveSvgElement[SVGElement] = {
    val $pieceState = $position.combineWithFn($isPlayerTurn, $scale) {
      case (position, isPlayerTurn, scale) => PieceState(color = color, position = position,
        flipPosition = FlipPosition, scale = scale, ghost = !isPlayerTurn)
    }
    g(className := "piece-avatar", PhysicalPiece($pieceState))
  }
}
