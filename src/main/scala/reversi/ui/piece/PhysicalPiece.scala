package reversi.ui.piece

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.api.L.{MapValueMapper, StringValueMapper, Val}
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGElement
import reversi.core.Color
import reversi.ui.models.{Fraction, PieceState}

object PhysicalPiece {
  val PieceRadius = 0.35

  val Thickness = 0.07
}

final class PhysicalPiece {

  import PhysicalPiece.*

  def apply($state: Signal[PieceState]): ReactiveSvgElement[SVGElement] =
    g(PieceBody($state))

  private def PieceBody($state: Signal[PieceState]): ReactiveSvgElement[SVGElement] = {
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
      PieceTop($state, Val(PieceRadius)))
  }

  private def PieceTop($state: Signal[PieceState],
                       $radius: Signal[Double]): ReactiveSvgElement[SVGElement] = {
    val $topColor = $state.map { state => if state.flipPosition.value <= 0.5 then state.color else state.color.opponent }
    val $topTransform = $state.map { state =>
      val yScale = yScaleForFlipPosition(state.flipPosition)
      s"scale(1,$yScale)"
    }
    val $bottomColor = $topColor.map(_.opponent)
    val $bottomTransform = $state.map { state =>
      val offset = Thickness * math.sin(state.flipPosition.value * 2 * math.Pi)
      val yScale = yScaleForFlipPosition(state.flipPosition)
      s"translate(0,$offset),scale(1,$yScale)"
    }
    g(
      g(transform <-- $bottomTransform, Disk($bottomColor, $radius)),
      g(transform <-- $topTransform, Disk($topColor, $radius))
    )
  }

  private def Disk($color: Signal[Color],
                   $radius: Signal[Double]): ReactiveSvgElement[SVGElement] = {
    val $classes = $color.map {
      case Color.Dark => "disk dark"
      case Color.Light => "disk light"
    }
    circle(className <-- $classes, r <-- $radius.map(_.toString))
  }

  private def yScaleForFlipPosition(flipPosition: Fraction): Double = {
    math.cos(flipPosition.value * math.Pi).abs
  }
}
