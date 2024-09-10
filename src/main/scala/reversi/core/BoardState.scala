package reversi.core

object BoardState {
  val empty: BoardState = new BoardState(Map.empty)
}

final class BoardState private(private val squares: Map[SquareIndex, Color]) {
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

  def difference(target: BoardState): Map[SquareIndex, PieceStateChange] = {
    val step1 = squares.foldLeft(Map.empty[SquareIndex, PieceStateChange]) {
      case (acc, (squareIndex, color)) =>
        target.get(squareIndex) match {
          case Some(piece) if piece == color.opponent => acc + (squareIndex -> PieceStateChange.Flip)
          case None => acc + (squareIndex -> PieceStateChange.Remove)
          case _ => acc
        }
    }
    target.squares.foldLeft(step1) {
      case (acc, (squareIndex, color)) =>
        squares.get(squareIndex) match {
          case None => acc + (squareIndex -> PieceStateChange.Add)
          case _ => acc
        }
    }
  }

}
