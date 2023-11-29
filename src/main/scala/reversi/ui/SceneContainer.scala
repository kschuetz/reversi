package reversi.ui

import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.SVGGElement

final class SceneContainer(SceneFrame: SceneFrame) {
  def apply(): ReactiveSvgElement[SVGGElement] =
    g()
}
