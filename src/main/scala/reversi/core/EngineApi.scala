package reversi.core

import scala.scalajs.js

@js.native
trait EngineApi extends js.Object {
  def reset(): Unit = js.native

  def getSquareState(idx: Int): Int = js.native

  def setSquareState(idx: Int, value: Int): Unit = js.native

  def setSeed(lo: Int, hi: Int): Unit = js.native

  def getSeedHi(): Int = js.native

  def getSeedLo(): Int = js.native

  def setCurrentPlayer(color: Int): Unit = js.native

  def computePlayerMove(maxCycles: Int): Int = js.native

  def rush(): Unit = js.native

  def isPlayerMoveReady(): Int = js.native

  def getPlayerMove(): Int = js.native

  def computeGameState(): Unit = js.native

  def generateRandomInt(bound: Int): Int = js.native
}
