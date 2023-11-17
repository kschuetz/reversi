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
        case Some(color) => engineApi.setSquareState(si.toInt, encodeSquareState(color))
        case _ => engineApi.setSquareState(si.toInt, 0)
      }
    }
    engineApi.setCurrentPlayer(encodePlayer(turn))
    engineApi.computeBeginTurnEvaluation() match {
      case 1 =>
        val winner = engineApi.getWinner()
        BeginTurnEvaluation.Win(decodePlayer(winner))
      case 2 =>
        BeginTurnEvaluation.Draw
      case _ =>
        val turnToPlay = decodePlayer(engineApi.getCurrentPlayer())
        val legalMoves = SquareIndex.All.foldLeft(Set.empty[SquareIndex]) {
          case (acc, si) =>
            if engineApi.isLegalMove(si.toInt) != 0 then acc + si else acc
        }
        val powerBalance = Power(engineApi.getPowerBalance())
        BeginTurnEvaluation.InProgress(turnToPlay, legalMoves, powerBalance)
    }
  }

  private def encodeSquareState(color: Color): Int =
    color match {
      case Color.Dark => 1
      case Color.Light => 2
    }

  private def encodePlayer(color: Color): Int =
    color match {
      case Color.Dark => 0
      case Color.Light => 1
    }

  private def decodePlayer(input: Int): Color =
    if input == 0 then Color.Dark else Color.Light
}
