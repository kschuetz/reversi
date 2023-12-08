package reversi.ui.piece

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.ui.models.PieceState

object PhysicalPiece {
  val PieceRadius = 0.35
}

final class PhysicalPiece {
  def apply($state: Signal[PieceState]): ReactiveSvgElement[SVGElement] =
    g()
}
