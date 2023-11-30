package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGSVGElement}
import reversi.ui.layout.{Pixels, SideChromeLayoutSettings}

final class SideChrome(PowerMeter: PowerMeter) {
  def apply($layout: Signal[SideChromeLayoutSettings],
            $height: Signal[Pixels]): ReactiveSvgElement[SVGElement] = {
    svg(className := "side-chrome",
      Backdrop($layout.map(_.width), $height)
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
