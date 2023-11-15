package reversi.core

object BoardState {
  val empty: BoardState = new BoardState(Map.empty)

  lazy val StandardStart: BoardState = empty
    .set(SquareIndex(27), Color.Light)
    .set(SquareIndex(28), Color.Dark)
    .set(SquareIndex(35), Color.Dark)
    .set(SquareIndex(36), Color.Light)
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
