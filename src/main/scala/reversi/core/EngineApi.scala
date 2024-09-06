package reversi.core

import scala.scalajs.js

@js.native
trait EngineApi extends js.Object {
  def reset(): Unit = js.native

  /**
   * @return 0: empty; 1: player; 2: opponent
   */
  def getSquareState(idx: Int): Int = js.native

  /**
   * @param value 0: empty; 1: player; 2: opponent
   */
  def setSquareState(idx: Int, value: Int): Unit = js.native

  def setSeed(lo: Int, hi: Int): Unit = js.native

  def getSeedHi(): Int = js.native

  def getSeedLo(): Int = js.native

  /**
   * // TODO: rename
   *
   * @return color 0: player, 1: opponent
   */
  def getCurrentPlayer(): Int = js.native

  def computePlayerMove(maxCycles: Int): Int = js.native

  def rush(): Unit = js.native

  def isPlayerMoveReady(): Int = js.native

  def getPlayerMove(): Int = js.native

  /**
   * Evaluates game state and makes it available for querying.
   *
   * @return 0: game in progress; 1: win (query getWinner() for result); 2: draw
   */
  def computeBeginTurnEvaluation(): Int = js.native

  /**
   * Query the winner in the case of a win. Only meaningful if computeBeginTurnEvaluation() returned 1 (win).
   *
   * @return 0: player; 1: opponent
   */
  def getWinner(): Int = js.native

  /**
   * Query if a square is a legal move. Only meaningful if computeBeginTurnEvaluation() returns 0 (game in progress).
   */
  def isLegalMove(idx: Int): Int = js.native

  /**
   * Query the power balance after calling computeBeginTurnEvaluation().
   *
   * @return -1 (player advantage) .. 1 (opponent advantage)
   */
  def getPowerBalance(): Double = js.native

  def generateRandomInt(bound: Int): Int = js.native

  /**
   * @param positionId 0: empty, 1: standard start
   */
  def initializeBoard(positionId: Int): Unit = js.native
}
