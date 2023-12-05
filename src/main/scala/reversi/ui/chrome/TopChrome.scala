package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.Val
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGSVGElement}
import reversi.core.Color
import reversi.ui.layout.{Pixels, TopChromeLayoutSettings}

final class TopChrome(PlayerPanel: PlayerPanel) {
  def apply($layout: Signal[TopChromeLayoutSettings],
            $width: Signal[Pixels]): ReactiveSvgElement[SVGElement] = {
    val $playerPanelWidth = $width.map(_ / 2)
    val $playerPanelHeight = $layout.map(_.height)

    val darkPlayerPanel = g(
      PlayerPanel(Color.Dark, $playerPanelWidth, $playerPanelHeight, Val("Player"), Val(0), Val(false))
    )
    val lightPlayerPanel = g(
      PlayerPanel(Color.Light, $playerPanelWidth, $playerPanelHeight, Val("Player"), Val(0), Val(false)),
      transform <-- $playerPanelWidth.map(x => s"translate($x,0)")
    )

    svg(className := "top-chrome",
      x := "0",
      y := "0",
      width <-- $width.map(_.toSvgString),
      height <-- $layout.map(_.height.toSvgString),
      darkPlayerPanel,
      lightPlayerPanel,
    )
  }
}
