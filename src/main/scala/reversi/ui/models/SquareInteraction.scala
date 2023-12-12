package reversi.ui.models

import reversi.core.SquareIndex

sealed trait SquareInteraction

object SquareInteraction {
  case class MouseOver(square: SquareIndex) extends SquareInteraction

  case class MouseOut(square: SquareIndex) extends SquareInteraction
}
