package reversi.core

import reversi.core.Color.{Dark, Light}

enum Color {
  case Dark
  case Light
}

extension (self: Color) {
  def opponent: Color = self match {
    case Light => Dark
    case Dark => Light
  }
}
