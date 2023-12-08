package reversi.ui

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGGElement}
import reversi.core.BoardState
import reversi.ui.board.PhysicalBoard
import reversi.ui.layout.Pixels
import reversi.ui.models.PieceTransforms

final class SceneFrame(PhysicalBoard: PhysicalBoard,
                       DynamicScene: DynamicScene) {
  def apply($width: Signal[Pixels],
            $height: Signal[Pixels],
            $boardRotation: Signal[Double],
            $boardState: Signal[BoardState],
            $pieceTransforms: Signal[PieceTransforms]): ReactiveSvgElement[SVGElement] = {
    val $transform = $width.combineWithFn($height, $boardRotation) {
      (width, height, boardRotation) =>
        val rotateTransform = if (boardRotation != 0) s",rotate($boardRotation)" else ""
        if width == height then {
          val translate = width.value / 2.0
          val scale = scaleForDimension(width)
          s"translate($translate,$translate),scale($scale)$rotateTransform"
        } else {
          val translateX = width.value / 2.0
          val translateY = height.value / 2.0
          val scaleX = scaleForDimension(width)
          val scaleY = scaleForDimension(height)
          s"translate($translateX,$translateY),scale($scaleX,$scaleY)$rotateTransform"
        }
    }
    g(Backdrop($width, $height),
      g(transform <-- $transform,
        PhysicalBoard(),
        DynamicScene($boardState, $pieceTransforms)))
  }

  private def Backdrop($width: Signal[Pixels],
                       $height: Signal[Pixels]) =
    rect(className := "backdrop",
      x := "0",
      y := "0",
      width <-- $width.map(_.toSvgString),
      height <-- $height.map(_.toSvgString),
    )

  private def scaleForDimension(dimension: Pixels): Double = {
    // when board is 800 x 800, scene needs to be scaled 90 times
    (dimension * 90).value / 800.0
  }
}
