package reversi.ui.models

import reversi.core.{Color, SquareIndex}

object PieceTransforms {
  val none: PieceTransforms = PieceTransforms(Map.empty, Map.empty)
}

case class PieceTransforms(flipPositions: Map[SquareIndex, Fraction],
                           ghostPieces: Map[SquareIndex, Color]) {
  def getFlipPosition(squareIndex: SquareIndex): Fraction =
    flipPositions.getOrElse(squareIndex, Fraction.Zero)
}
