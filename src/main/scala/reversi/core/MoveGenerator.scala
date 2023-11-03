package reversi.core

class MoveGenerator {

  def generateMoves(player: Color, board: BoardStateRead): BoardMask = {
    var result = BoardMask.empty
    val unoccupied = board.unoccupied
    val myPieces = board.pieces(player)
    val opponentPieces = board.pieces(player.opponent)
    SquareIndex.foreach { si =>
      if unoccupied.isSet(si) then {
        val neighborIter = si.neighborDirections.iterator
        var found = false
        while (!found && neighborIter.hasNext) {
          val direction = neighborIter.next()
          val shiftDirection = direction.opposite
          found = walk(si, myPieces, opponentPieces, shiftDirection)
        }
        if found then {
          result = result | BoardMask.square(si)
        }
      }
    }
    result
  }

  private def walk(squareIndex: SquareIndex,
                   player: BoardMask,
                   opponent: BoardMask,
                   shiftDirection: Direction): Boolean = {
    var op = opponent.shift(shiftDirection)
    if op.isSet(squareIndex) then {
      op = op.shift(shiftDirection)
      var p = player.shift(shiftDirection).shift(shiftDirection)
      var result = p.isSet(squareIndex)
      var loop = true
      while (loop && !result) {
        if !op.isSet(squareIndex) then loop = false
        else {
          p = p.shift(shiftDirection)
          op = op.shift(shiftDirection)
          result = p.isSet(squareIndex)
        }
      }
      result
    } else false
  }
}
