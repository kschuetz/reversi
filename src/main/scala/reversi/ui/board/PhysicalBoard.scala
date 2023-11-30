package reversi.ui.board

import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGGElement}

object PhysicalBoard {

  inline def SquareSize: Double = 1.0

  private inline def SquareSizeStr = SquareSize.toString

  val squareCenterOffset: Double = SquareSize / 2
  private val boardCenterOffset = 3.5 * SquareSize

  object Css {
    val boardSquare = "board-square"
  }

}

final class PhysicalBoard {

  import PhysicalBoard.*

  def apply(): ReactiveSvgElement[SVGElement] = g()

  private def Square(centerX: Double, centerY: Double) =
    rect(className := Css.boardSquare,
      x := (centerX - squareCenterOffset).toString,
      y := (centerY - squareCenterOffset).toString,
      width := SquareSizeStr,
      height := SquareSizeStr,
    )
}
