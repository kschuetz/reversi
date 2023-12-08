package reversi.ui.models

import reversi.core.Color

case class PieceState(color: Color,
                      xpos: Double,
                      ypos: Double,
                      scale: Double = 1.0,
                      flipPosition: Fraction = Fraction.Zero,
                      ghost: Boolean = false,
                      simplified: Boolean = false)
