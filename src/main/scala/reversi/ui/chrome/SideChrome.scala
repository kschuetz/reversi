package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGSVGElement
import reversi.ui.chrome.SideChrome.Props
import reversi.ui.layout.{Pixels, SideChromeLayoutSettings}

object SideChrome {
  case class Props(layout: Signal[SideChromeLayoutSettings],
                   height: Signal[Pixels])
}

final class SideChrome(PowerMeter: PowerMeter) {
  def apply(props: Props): ReactiveSvgElement[SVGSVGElement] = {
    svg(className := "side-chrome",
      Backdrop(props.layout.map(_.width), props.height)
    )
  }

  private def Backdrop($width: Signal[Pixels],
                       $height: Signal[Pixels]) =
    rect(className := "side-chrome-backdrop",
      x := "0",
      y := "0",
      width <-- $width.map(_.toSvgString),
      height <-- $height.map(_.toSvgString),
    )
}
