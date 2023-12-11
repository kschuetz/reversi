package reversi.ui

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGSVGElement}
import reversi.core.{BoardState, Color}
import reversi.ui.chrome.{SideChrome, TopChrome}
import reversi.ui.layout.ScreenLayoutSettings
import reversi.ui.models.PieceTransforms

final class GameScreen(SceneContainer: SceneContainer,
                       TopChrome: TopChrome,
                       SideChrome: SideChrome) {
  def apply($layout: Signal[ScreenLayoutSettings],
            $boardRotation: Signal[Double],
            $boardState: Signal[BoardState],
            $pieceTransforms: Signal[PieceTransforms],
            $turnToPlay: Signal[Color]): ReactiveSvgElement[SVGElement] = {
    val $sceneContainerTransform = $layout.map(l => s"translate(0,${l.gameSceneY})")
    val $sideChromeTransform = $layout.map { l =>
      s"translate(${l.sideChromeX},${l.gameSceneY})"
    }
    svg(idAttr := "game-screen",
      width <-- $layout.map(_.totalWidth.toSvgString),
      height <-- $layout.map(_.totalHeight.toSvgString),
      g(TopChrome($layout.map(_.topChrome), $layout.map(_.gameSceneWidth), $turnToPlay)),
      g(SceneContainer($layout.map(_.gameSceneWidth), $layout.map(_.gameSceneHeight), $boardRotation,
        $boardState, $pieceTransforms),
        transform <-- $sceneContainerTransform),
      g(SideChrome($layout.map(_.sideChrome), $layout.map(_.gameSceneHeight)),
        transform <-- $sideChromeTransform),
    )
  }
}
