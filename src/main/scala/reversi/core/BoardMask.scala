package reversi.core

import scala.annotation.targetName

opaque type BoardMask = Long

object BoardMask {
  def empty: BoardMask = 0L

  def apply(value: Long): BoardMask = value

  def apply(top: Int, bottom: Int): BoardMask = (bottom.toLong << 32) | top

  inline def position(p: Int): BoardMask = 1.toLong << p

  def positions(ps: Int*): BoardMask = ps.foldLeft(0L) {
    case (acc, p) => acc | position(p)
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

    inline def isSet(position: Int): Boolean = (value & (1.toLong << position)) != 0

    inline def set(position: Int): BoardMask = value | (1.toLong << position)

    inline def clear(position: Int): BoardMask = value & (~(1.toLong << position))
  }
}
