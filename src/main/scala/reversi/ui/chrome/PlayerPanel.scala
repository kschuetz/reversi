package reversi.ui.chrome

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.core.Color
import reversi.ui.layout.Pixels
import reversi.ui.util.CssHelpers

final class PlayerPanel {
  def apply(side: Color,
            $width: Signal[Pixels],
            $height: Signal[Pixels],
            $playerName: Signal[String],
            $clockSeconds: Signal[Int],
            $isPlayerTurn: Signal[Boolean]): ReactiveSvgElement[SVGElement] = {
    g(Backdrop(side, $width, $height))
  }

  private def Backdrop(side: Color,
                       $width: Signal[Pixels],
                       $height: Signal[Pixels]): ReactiveSvgElement[SVGElement] =
    rect(className := s"player-panel-backdrop ${CssHelpers.playerColorClass(side)}",
      x := "0",
      y := "0",
      width <-- $width.map(_.toSvgString),
      height <-- $height.map(_.toSvgString))

}

/*
case class Props(widthPixels: Int,
                 heightPixels: Int,
                 side: Side,
                 playerName: String,
                 isComputerPlayer: Boolean,
                 clockSeconds: Int,
                 scoreDisplay: Option[String],
                 isPlayerTurn: Boolean,
                 waitingForMove: Boolean,
                 endingTurn: Boolean,
                 clockVisible: Boolean,
                 jumpIndicator: Boolean,
                 thinkingIndicator: Boolean,
                 rushButtonEnabled: Boolean,
                 applicationCallbacks: ApplicationCallbacks)
 */