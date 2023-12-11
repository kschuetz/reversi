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
            $width: Signal[Pixels],
            $turnToPlay: Signal[Option[Color]]): ReactiveSvgElement[SVGElement] = {
    val $playerPanelWidth = $width.map(_ / 2)
    val $playerPanelHeight = $layout.map(_.height)

    val darkPlayerPanel = g(
      makePlayerPanel(Color.Dark, $playerPanelWidth, $playerPanelHeight, Val("Player"), Val(0),
        $turnToPlay)
    )
    val lightPlayerPanel = g(
      makePlayerPanel(Color.Light, $playerPanelWidth, $playerPanelHeight, Val("Player"), Val(0),
        $turnToPlay),
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

  private def makePlayerPanel(color: Color,
                              $panelWidth: Signal[Pixels],
                              $panelHeight: Signal[Pixels],
                              $playerName: Signal[String],
                              $clockSeconds: Signal[Int],
                              $turnToPlay: Signal[Option[Color]]) =
    PlayerPanel(color, $panelWidth, $panelHeight, Val("Player"), Val(0),
      $turnToPlay.map(_.map(_ == color)))

}
