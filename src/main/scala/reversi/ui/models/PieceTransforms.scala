package reversi.ui.models

import reversi.core.{Color, SquareIndex}

object PieceTransforms {
  val none: PieceTransforms = PieceTransforms(Map.empty, Map.empty)
}

case class PieceTransforms(flipPositions: Map[SquareIndex, Fraction],
                           ghostPieces: Map[SquareIndex, Color]) {
  def getFlipPosition(squareIndex: SquareIndex): Fraction =
    flipPositions.getOrElse(squareIndex, Fraction.Zero)

  def setFlipPosition(squareIndex: SquareIndex, value: Fraction): PieceTransforms =
    if value == Fraction.Zero then copy(flipPositions = flipPositions - squareIndex)
    else copy(flipPositions = flipPositions + (squareIndex -> value))

  def setGhost(squareIndex: SquareIndex, color: Color): PieceTransforms =
    copy(ghostPieces = ghostPieces + (squareIndex -> color))
}
