package reversi

import reversi.core.*

import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Main")
object Main {

  @JSExport
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    println(s"Board coordinates: ${SquareIndex.Min}..${SquareIndex.Max}")
    js.Dynamic.global.window._reversiWasm.asInstanceOf[Promise[js.Dynamic]]
      .`then`(result => {
        println("Loaded")

        val engineApi = result.exports.asInstanceOf[EngineApi]
        val engine = new Engine(engineApi)

        println(engine.evaluateGameState(Color.Dark, BoardState.StandardStart))
        println(engine.evaluateGameState(Color.Light, BoardState.StandardStart))
      })
  }

}
