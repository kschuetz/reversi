package reversi.core

import reversi.core.Direction.*

opaque type SquareIndex = Int

object SquareIndex {
  def apply(i: Int): SquareIndex = i

  inline def Min: SquareIndex = 0

  inline def Max: SquareIndex = Row.Count * Column.Count - 1

  val All: Vector[SquareIndex] = Min.to(Max).toVector

  inline def OutOfBounds: SquareIndex = -1

  inline def at(row: Row, column: Column): SquareIndex =
    if row.isInBounds && column.isInBounds then (row * 8) + column
    else OutOfBounds

  def foreach(f: SquareIndex => Unit): Unit =
    All.foreach(f)

  extension (self: SquareIndex) {
    def toInt: Int = self

    def +(offset: Int): SquareIndex = toInt + offset

    def -(offset: Int): SquareIndex = toInt - offset

    inline def isInBounds: Boolean = toInt >= Min && toInt <= Max

    inline def row: Row = self / 8

    inline def column: Column = self % 8

    def neighbor(direction: Direction): SquareIndex =
      direction match {
        case N => at(row - 1, column)
        case NE => at(row - 1, column + 1)
        case E => at(row, column + 1)
        case SE => at(row + 1, column + 1)
        case S => at(row + 1, column)
        case SW => at(row + 1, column - 1)
        case W => at(row, column - 1)
        case NW => at(row - 1, column - 1)
      }
  }

}

opaque type Row = Int

object Row {
  def apply(i: Int): Row = i

  inline def Count: Int = 8

  inline def Min: Row = 0

  inline def Max: Row = Count - 1

  val All: Vector[Row] = Min.to(Max).toVector

  extension (self: Row) {
    def toInt: Int = self

    def toDouble: Double = self.toDouble

    inline def isInBounds: Boolean = toInt >= Min && toInt <= Max

    def +(offset: Int): Row = toInt + offset

    def -(offset: Int): Row = toInt - offset

    inline def at(column: Column): SquareIndex = (toInt * Column.Count) + column
  }
}

opaque type Column = Int

object Column {
  def apply(i: Int): Column = i

  inline def Count: Int = 8

  inline def Min: Column = 0

  inline def Max: Column = Count - 1

  val All: Vector[Column] = Min.to(Max).toVector

  extension (self: Column) {
    def toInt: Int = self

    def toDouble: Double = self.toDouble

    inline def isInBounds: Boolean = toInt >= Min && toInt <= Max

    def +(offset: Int): Column = toInt + offset

    def -(offset: Int): Column = toInt - offset

    inline def at(row: Row): SquareIndex = (row.toInt * Count) + toInt
  }
}
