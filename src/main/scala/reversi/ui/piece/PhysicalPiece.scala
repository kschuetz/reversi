package reversi.ui.piece

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.{MapValueMapper, StringValueMapper, Val}
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.core.Color
import reversi.ui.models.PieceState

object PhysicalPiece {
  val PieceRadius = 0.35
}

final class PhysicalPiece {

  import PhysicalPiece.*

  def apply($state: Signal[PieceState]): ReactiveSvgElement[SVGElement] =
    g(PieceBody($state))

  private def PieceBody($state: Signal[PieceState]): ReactiveSvgElement[SVGElement] = {
    val $color = $state.map(_.color)
    val $classes = $state.map { state =>
      Map("piece" -> true,
        "dark" -> (state.color == Color.Dark),
        "light" -> (state.color == Color.Light),
        "ghost-piece" -> state.ghost)
    }
    val $transform = $state.map { state =>
      s"translate(${state.position.x},${state.position.y}),scale(${state.scale})"
    }
    g(cls <-- $classes, transform <-- $transform,
      Disk($color, Val(PieceRadius)))
  }

  private def Disk($color: Signal[Color],
                   $radius: Signal[Double]): ReactiveSvgElement[SVGElement] = {
    val $classes = $color.map {
      case Color.Dark => "disk dark"
      case Color.Light => "disk light"
    }
    circle(className <-- $classes, r <-- $radius.map(_.toString))
  }

}
