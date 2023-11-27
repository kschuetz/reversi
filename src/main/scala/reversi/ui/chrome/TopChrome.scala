package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGSVGElement
import reversi.ui.chrome.TopChrome.Props
import reversi.ui.layout.{Pixels, TopChromeLayoutSettings}

object TopChrome {
  case class Props(layout: Signal[TopChromeLayoutSettings],
                   width: Signal[Pixels])
}

final class TopChrome {
  def apply(props: Props): ReactiveSvgElement[SVGSVGElement] = {
    svg(className := "top-chrome",
      x := "0",
      y := "0",
      width <-- props.width.map(_.toSvgString),
      height <-- props.layout.map(_.height.toSvgString),
    )
  }
}
