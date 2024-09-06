package reversi.core

final class Engine(engineApi: EngineApi) {
  def setSeed(seed: Seed): Unit = {
    engineApi.setSeed(seed.lo, seed.hi)
  }

  def getSeed: Seed = {
    Seed(engineApi.getSeedLo(), engineApi.getSeedHi())
  }

  def generateRandomInt(bound: Int): Int =
    engineApi.generateRandomInt(bound)

  def computeBeginTurnEvaluation(turn: Color, boardState: BoardState): BeginTurnEvaluation = {
    engineApi.reset()
    SquareIndex.All.foreach { si =>
      boardState.get(si) match {
        case Some(color) => engineApi.setSquareState(si.toInt, encodeRelativeSquareState(turn, color))
        case _ => engineApi.setSquareState(si.toInt, 0)
      }
    }
    engineApi.computeBeginTurnEvaluation() match {
      case EngineConstants.Win =>
        val winner = engineApi.getWinner()
        BeginTurnEvaluation.Win(decodePlayer(turn, winner))
      case EngineConstants.Draw =>
        BeginTurnEvaluation.Draw
      case _ =>
        val turnToPlay = decodePlayer(turn, engineApi.getCurrentPlayer())
        val legalMoves = SquareIndex.All.
          foldLeft(Set.empty[SquareIndex]) {
            case (acc, si) =>
              if engineApi.isLegalMove(si.toInt) != 0 then acc + si else acc
          }
        val powerBalance = Power(engineApi.getPowerBalance())
        BeginTurnEvaluation.InProgress(turnToPlay, legalMoves, powerBalance)
    }
  }

  def initializeBoard: BoardState = {
    engineApi.initializeBoard(EngineConstants.StandardStartPosition)
    SquareIndex.All.foldLeft(BoardState.empty) {
      case (acc, si) =>
        engineApi.getSquareState(si.toInt) match {
          case EngineConstants.Player => acc.set(si, Color.Dark)
          case EngineConstants.Opponent => acc.set(si, Color.Light)
          case _ => acc
        }
    }
  }

  private def encodeRelativeSquareState(turn: Color, color: Color): Int =
    if turn == color then 1 else 2

  private def decodePlayer(turn: Color, input: Int): Color =
    if input == 0 then turn else turn.opponent
}
