package reversi.core

import reversi.core.Color.{Dark, Light}

enum Color {
  case Light
  case Dark
}

extension (self: Color) {
  def opponent: Color = self match {
    case Light => Dark
    case Dark => Light
  }
}
