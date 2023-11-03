package reversi.core

import reversi.core.Direction.*

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

    def shift(direction: Direction): BoardMask =
      direction match {
        case N => shiftN
        case NE => shiftNE
        case E => shiftE
        case SE => shiftSE
        case S => shiftS
        case SW => shiftSW
        case W => shiftW
        case NW => shiftNW
      }

    inline def shiftN: BoardMask = value >> 8

    inline def shiftS: BoardMask = value << 8

    inline def shiftE: BoardMask = (value << 1) & BoardMasks.EDGE_E.complement

    inline def shiftW: BoardMask = (value >> 1) & BoardMasks.EDGE_W.complement

    inline def shiftNE: BoardMask = (value >> 7) & BoardMasks.EDGE_NE.complement

    inline def shiftSE: BoardMask = (value << 9) & BoardMasks.EDGE_SE.complement

    inline def shiftSW: BoardMask = (value << 7) & BoardMasks.EDGE_SW.complement

    inline def shiftNW: BoardMask = (value >> 9) & BoardMasks.EDGE_NW.complement
  }
}

object BoardMasks {
  inline def EDGE_N: BoardMask = BoardMask(0xFF)

  inline def EDGE_E: BoardMask = BoardMask(0x80L | (0x80L << 8) | (0x80L << 16) | (0x80L << 24) |
    (0x80L << 32) | (0x80L << 40) | (0x80L << 48) | (0x80L << 56))

  inline def EDGE_S: BoardMask = BoardMask(0xFFL << 56)

  inline def EDGE_W: BoardMask = BoardMask(0x01L | (0x01L << 8) | (0x01L << 16) | (0x01L << 24) |
    (0x01L << 32) | (0x01L << 40) | (0x01L << 48) | (0x01L << 56))

  inline def EDGE_NE: BoardMask = EDGE_N | EDGE_E

  inline def EDGE_SE: BoardMask = EDGE_S | EDGE_E

  inline def EDGE_SW: BoardMask = EDGE_S | EDGE_W

  inline def EDGE_NW: BoardMask = EDGE_N | EDGE_W
}
