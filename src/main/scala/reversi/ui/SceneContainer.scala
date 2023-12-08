package reversi.ui

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.core.BoardState
import reversi.ui.layout.Pixels
import reversi.ui.models.PieceTransforms

final class SceneContainer(SceneFrame: SceneFrame) {
  def apply($width: Signal[Pixels],
            $height: Signal[Pixels],
            $boardRotation: Signal[Double],
            $boardState: Signal[BoardState],
            $pieceTransforms: Signal[PieceTransforms]): ReactiveSvgElement[SVGElement] =
    svg(idAttr := "game-scene",
      width <-- $width.map(_.toSvgString),
      height <-- $height.map(_.toSvgString),
      SceneFrame($width, $height, $boardRotation, $boardState, $pieceTransforms))
}
