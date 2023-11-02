package reversi.core

import scala.annotation.targetName

opaque type BoardMask = Long

object BoardMask {
  def empty: BoardMask = 0L

  def apply(value: Long): BoardMask = value

  def apply(top: Int, bottom: Int): BoardMask = (bottom.toLong << 32) | top

  inline def square(i: SquareIndex): BoardMask = 1.toLong << i.toInt

  def squares(ps: SquareIndex*): BoardMask = ps.foldLeft(0L) {
    case (acc, p) => acc | square(p)
  }

  extension (self: BoardMask) {
    def value: Long = self

    @targetName("and")
    inline def &(other: BoardMask): BoardMask = value & other.value

    @targetName("or")
    inline def |(other: BoardMask): BoardMask = value | other.value

    inline def complement: BoardMask = ~value

    inline def top: Int = value.toInt

    inline def bottom: Int = (value >> 32).toInt

    inline def isSet(i: SquareIndex): Boolean = (value & (1.toLong << i.toInt)) != 0

    inline def set(i: SquareIndex): BoardMask = value | (1.toLong << i.toInt)

    inline def clear(i: SquareIndex): BoardMask = value & (~(1.toLong << i.toInt))

    def foreach(f: SquareIndex => Unit): Unit =
      SquareIndex.All.foreach(idx => if isSet(idx) then f(idx))

  }
}
