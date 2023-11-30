package reversi.core

import org.scalajs.dom

class Application(engine: Engine) {
  def start(gameHost: dom.Element, dialogHost: dom.Element): Unit = {
    println(engine.computeBeginTurnEvaluation(Color.Dark, BoardState.StandardStart))
    println(engine.computeBeginTurnEvaluation(Color.Light, BoardState.StandardStart))
    println(engine.computeBeginTurnEvaluation(Color.Dark, BoardState.empty.set(SquareIndex(28), Color.Dark)))
    println(engine.computeBeginTurnEvaluation(Color.Light, BoardState.empty.set(SquareIndex(28), Color.Dark)))
    println(engine.computeBeginTurnEvaluation(Color.Light, BoardState.empty.set(SquareIndex(28), Color.Light)))
    println(engine.computeBeginTurnEvaluation(Color.Dark, BoardState.empty))
  }
}
