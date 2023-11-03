package reversi

import reversi.core.*

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Main")
object Main {

  @JSExport
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    println(s"Board coordinates: ${SquareIndex.Min}..${SquareIndex.Max}")
    println(SquareIndex(0).neighborDirections.iterator.toVector)
    println(SquareIndex(1).neighborDirections.iterator.toVector)
    println(SquareIndex(20).neighborDirections.iterator.toVector)
    println(SquareIndex(63).neighborDirections.iterator.toVector)
  }

}
