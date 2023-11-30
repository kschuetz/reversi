package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGGElement}
import reversi.core.Power
import reversi.ui.layout.Pixels

final class PowerMeter {
  def apply($width: Signal[Pixels],
            $height: Signal[Pixels],
            $power: Signal[Power]): ReactiveSvgElement[SVGElement] =
    g(className := "power-meter",
      rect(width <-- $width.map(_.toString),
        height <-- $height.map(_.toString)))
}
