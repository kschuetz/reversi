package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGSVGElement
import reversi.ui.layout.{Pixels, TopChromeLayoutSettings}

final class TopChrome {
  def apply($layout: Signal[TopChromeLayoutSettings],
            $width: Signal[Pixels]): ReactiveSvgElement[SVGSVGElement] = {
    svg(className := "top-chrome",
      x := "0",
      y := "0",
      width <-- $width.map(_.toSvgString),
      height <-- $layout.map(_.height.toSvgString),
    )
  }
}
