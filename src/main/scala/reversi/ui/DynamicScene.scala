package reversi.ui

import com.raquo.laminar.api.L.svg.*
import com.raquo.laminar.nodes.ReactiveSvgElement
import org.scalajs.dom.{SVGElement, SVGGElement}

final class DynamicScene {
  def apply(): ReactiveSvgElement[SVGElement] = g()
}
