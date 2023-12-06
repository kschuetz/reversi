package reversi.ui.board

import com.raquo.laminar.api.L.seqToModifier
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGGElement}

object PhysicalBoard {

  inline def SquareSize: Double = 1.0

  private inline def SquareSizeStr = SquareSize.toString

  val SquareCenterOffset: Double = SquareSize / 2
  private val BoardCenterOffset = 3.5 * SquareSize

  object Css {
    val boardSquare = "board-square"
    val boardBorder = "board-border"
  }

}

final class PhysicalBoard {

  import PhysicalBoard.*

  def apply(): ReactiveSvgElement[SVGElement] = {
    val squares = for {
      row <- 0 to 7
      centerY = row - BoardCenterOffset
      col <- 0 to 7
      centerX = col - BoardCenterOffset
    } yield Square(centerX, centerY)
    g(BoardBorder(0.3),
      seqToModifier(squares))
  }

  private def Square(centerX: Double, centerY: Double) =
    rect(className := Css.boardSquare,
      x := (centerX - SquareCenterOffset).toString,
      y := (centerY - SquareCenterOffset).toString,
      width := SquareSizeStr,
      height := SquareSizeStr,
    )

  private def BoardBorder(thickness: Double) = {
    val origin = -4 - thickness
    val w = 8 + 2 * thickness
    rect(className := Css.boardBorder,
      x := origin.toString,
      y := origin.toString,
      width := w.toString,
      height := w.toString,
    )
  }

}
