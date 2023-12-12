package reversi.ui

import com.raquo.airstream.core.Signal
import com.raquo.airstream.eventbus.WriteBus
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.core.GameState
import reversi.ui.layout.Pixels
import reversi.ui.models.{PieceTransforms, SquareInteraction}

final class SceneContainer(SceneFrame: SceneFrame) {
  def apply($width: Signal[Pixels],
            $height: Signal[Pixels],
            $boardRotation: Signal[Double],
            $gameState: Signal[GameState],
            $pieceTransforms: Signal[PieceTransforms],
            squareInteractions: WriteBus[SquareInteraction]): ReactiveSvgElement[SVGElement] =
    svg(idAttr := "game-scene",
      width <-- $width.map(_.toSvgString),
      height <-- $height.map(_.toSvgString),
      SceneFrame($width, $height, $boardRotation, $gameState, $pieceTransforms, squareInteractions))
}
