package reversi.ui.board

import com.raquo.airstream.core.Signal
import com.raquo.airstream.eventbus.WriteBus
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.api.L.{onClick, onMouseOut, onMouseOver, given}
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.core.SquareIndex
import reversi.ui.models.SquareInteraction

object BoardOverlayButtons {
  object Css {
    val squareOverlay = "square-overlay"
  }
}

final class BoardOverlayButtons {

  import BoardOverlayButtons.*

  def apply(squareInteractions: WriteBus[SquareInteraction],
            $welcomeToClick: Signal[Set[SquareIndex]]): ReactiveSvgElement[SVGElement] = {
    val squares = for {
      squareIndex <- SquareIndex.All
    } yield Overlay(squareInteractions, $welcomeToClick.map(_.contains(squareIndex)), squareIndex)
    g(squares)
  }

  private def Overlay(squareInteractions: WriteBus[SquareInteraction],
                      $welcomeToClick: Signal[Boolean],
                      squareIndex: SquareIndex) = {
    val $className = $welcomeToClick.map { welcome =>
      Map(Css.squareOverlay -> true, "welcome" -> welcome)
    }
    val centerX = squareIndex.column.toDouble - PhysicalBoard.BoardCenterOffset
    val centerY = squareIndex.row.toDouble - PhysicalBoard.BoardCenterOffset
    rect(className <-- $className,
      x := (centerX - PhysicalBoard.SquareCenterOffset).toString,
      y := (centerY - PhysicalBoard.SquareCenterOffset).toString,
      width := PhysicalBoard.SquareSizeStr,
      height := PhysicalBoard.SquareSizeStr,
      onMouseOver.mapToStrict(SquareInteraction.MouseOver(squareIndex)) --> squareInteractions,
      onMouseOut.mapToStrict(SquareInteraction.MouseOut(squareIndex)) --> squareInteractions,
      onClick.mapToStrict(SquareInteraction.Click(squareIndex)) --> squareInteractions,
    )
  }
}
