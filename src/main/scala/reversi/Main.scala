package reversi

import reversi.core.*

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Main")
object Main {

  @JSExport
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    println(s"Board coordinates: ${SquareIndex.Min}..${SquareIndex.Max}")
    println(BoardMasks.EDGE_W)
    println(BoardMasks.EDGE_E)
    println(BoardMasks.EDGE_NE)
    println(BoardMasks.EDGE_NW)
  }

}
