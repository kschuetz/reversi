package reversi.ui

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.ui.layout.Pixels

final class SceneContainer(SceneFrame: SceneFrame) {
  def apply($width: Signal[Pixels],
            $height: Signal[Pixels],
            $boardRotation: Signal[Double]): ReactiveSvgElement[SVGElement] =
    svg(idAttr := "game-scene",
      width <-- $width.map(_.toSvgString),
      height <-- $height.map(_.toSvgString),
      SceneFrame($width, $height, $boardRotation))
}
