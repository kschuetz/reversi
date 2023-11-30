package reversi.ui

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGGElement}
import reversi.ui.board.PhysicalBoard
import reversi.ui.layout.Pixels

final class SceneFrame(PhysicalBoard: PhysicalBoard,
                       DynamicScene: DynamicScene) {
  def apply($width: Signal[Pixels],
            $height: Signal[Pixels],
            $boardRotation: Signal[Double]): ReactiveSvgElement[SVGElement] = {
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
    g(transform <-- $transform,
      Backdrop($width, $height),
      g(PhysicalBoard(),
        DynamicScene()))
  }


  /*
  def render(props: Props): VdomElement = {
    val Props(model, callbacks, sceneContainerContext, widthPixels, heightPixels) = props
    val screenToBoard = makeScreenToBoard(sceneContainerContext)

    val boardRotation = model.getBoardRotation
    val rotateTransform = if (boardRotation != 0) s",rotate($boardRotation)" else ""

    val dynamicSceneProps = DynamicScene.Props(model, callbacks, sceneContainerContext, screenToBoard)

    val transform = if (widthPixels == heightPixels) {
      val translate = widthPixels / 2.0
      val scale = scaleForDimension(widthPixels)
      s"translate($translate,$translate),scale($scale)$rotateTransform"
    } else {
      val translateX = widthPixels / 2.0
      val translateY = heightPixels / 2.0
      val scaleX = scaleForDimension(widthPixels)
      val scaleY = scaleForDimension(heightPixels)
      s"translate($translateX,$translateY),scale($scaleX,$scaleY)$rotateTransform"
    }

    val physicalBoardElement = physicalBoard.create()
    val dynamicSceneElement = dynamicScene.create(dynamicSceneProps)
    svg.<.g(
      Backdrop((widthPixels, heightPixels)),
      svg.<.g.withRef(playfieldRef)(
        svg.^.transform := transform,
        physicalBoardElement,
        dynamicSceneElement
      )
    )
  }
   */

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
