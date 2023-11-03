package reversi.core

import reversi.core.Direction.*

import scala.annotation.targetName

opaque type DirectionMask = Int

object DirectionMask {
  def of(direction: Direction): DirectionMask =
    direction match {
      case N => 8
      case NE => 12
      case E => 4
      case SE => 6
      case S => 2
      case SW => 3
      case W => 1
      case NW => 9
    }

  inline def empty: DirectionMask = 0

  def apply(value: Int): DirectionMask = value & 15

  extension (self: DirectionMask) {
    def value: Int = self

    @targetName("and")
    inline def &(other: DirectionMask): DirectionMask = value & other.value

    @targetName("or")
    inline def |(other: DirectionMask): DirectionMask = value | other.value

    def contains(direction: Direction): Boolean = {
      val mask = of(direction)
      (value & mask) == mask
    }

    def iterator: Iterator[Direction] = directionLists(self & 15).iterator
  }

  private val directionLists = new Array[Array[Direction]](16)
  directionLists(0) = Array.empty
  directionLists(1) = Array(W)
  directionLists(2) = Array(S)
  directionLists(3) = Array(S, W, SW)
  directionLists(4) = Array(E)
  directionLists(5) = Array(E, W)
  directionLists(6) = Array(E, S, SE)
  directionLists(7) = Array(E, S, W, SE, SW)
  directionLists(8) = Array(N)
  directionLists(9) = Array(N, W, NW)
  directionLists(10) = Array(N, S)
  directionLists(11) = Array(N, S, W, SW, NW)
  directionLists(12) = Array(N, E, NE)
  directionLists(13) = Array(N, E, W, NE, NW)
  directionLists(14) = Array(N, E, S, NE, SE)
  directionLists(15) = Array(N, E, S, W, NE, SE, SW, NW)

  /*

  8421 NESW

  0000
  0001 ---W       W
  0010 --S-       S
  0011 --SW       S, W, SW
  0100 -E--       E
  0101 -E-W       E, W
  0110 -ES-       E, S, SE
  0111 -ESW       E, S, W, SE, SW
  1000 N---       N
  1001 N--W       N, W, NW
  1010 N-S-       N, S
  1011 N-SW       N, S, W, NW, SW
  1100 NE--       N, E, NE
  1101 NE-W       N, E, NE, NW
  1110 NES-       N, E, S, NE, SE
  1111 NESW       N, E, S, W, NE, SE, SW, NW

  case N
  case NE
  case E
  case SE
  case S
  case SW
  case W
  case NW
   */
}
