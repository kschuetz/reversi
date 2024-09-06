package reversi.core

object BoardState {
  val empty: BoardState = new BoardState(Map.empty)
}

final class BoardState private(squares: Map[SquareIndex, Color]) {
  def set(squareIndex: SquareIndex, color: Color): BoardState = {
    if squareIndex.isInBounds then {
      new BoardState(squares + (squareIndex -> color))
    } else this
  }

  def unset(squareIndex: SquareIndex): BoardState =
    if squareIndex.isInBounds then {
      new BoardState(squares - squareIndex)
    } else this

  def get(squareIndex: SquareIndex): Option[Color] =
    squares.get(squareIndex)
}
