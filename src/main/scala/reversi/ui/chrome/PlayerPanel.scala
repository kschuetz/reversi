package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.core.Color
import reversi.ui.layout.Pixels
import reversi.ui.models.Point
import reversi.ui.util.CssHelpers

final class PlayerPanel(PieceAvatar: PieceAvatar) {
  def apply(side: Color,
            $width: Signal[Pixels],
            $height: Signal[Pixels],
            $playerName: Signal[String],
            $clockSeconds: Signal[Int],
            $isPlayerTurn: Signal[Option[Boolean]]): ReactiveSvgElement[SVGElement] = {
    g(Backdrop(side, $width, $height),
      makeAvatar(side, $height, $isPlayerTurn))
  }

  private def Backdrop(side: Color,
                       $width: Signal[Pixels],
                       $height: Signal[Pixels]): ReactiveSvgElement[SVGElement] =
    rect(className := s"player-panel-backdrop ${CssHelpers.playerColorClass(side)}",
      x := "0",
      y := "0",
      width <-- $width.map(_.toSvgString),
      height <-- $height.map(_.toSvgString))

  private def makeAvatar(color: Color,
                         $height: Signal[Pixels],
                         $isPlayerTurn: Signal[Option[Boolean]]): ReactiveSvgElement[SVGElement] = {
    val $scale = $height.map(h => h.value * 0.8)
    val $position = $scale.combineWithFn($height) {
      case (scale, height) =>
        Point(scale - 10, height.value / 2.0)
    }
    PieceAvatar(color, $position, $isPlayerTurn, $scale)
  }

}
