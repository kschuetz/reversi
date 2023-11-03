package reversi.core

import reversi.core.Direction.*

enum Direction {
  case N
  case NE
  case E
  case SE
  case S
  case SW
  case W
  case NW
}

extension (self: Direction) {
  def opposite: Direction = self match {
    case N => S
    case NE => SW
    case E => W
    case SE => NW
    case S => N
    case SW => NE
    case W => E
    case NW => SE
  }
}
