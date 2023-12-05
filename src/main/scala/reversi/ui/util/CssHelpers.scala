package reversi.ui.util

import reversi.core.Color

object CssHelpers {
  def playerColorClass(color: Color): String =
    color match {
      case Color.Dark => "dark"
      case Color.Light => "light"
    }

  def turnStatus(isPlayerTurn: Boolean): String =
    if (isPlayerTurn) "my-turn" else "not-my-turn"
}
