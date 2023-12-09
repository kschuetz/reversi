package reversi.core

enum Color {
  case Dark
  case Light
}

object Color {
  extension (self: Color) {
    def opponent: Color = self match {
      case Light => Dark
      case Dark => Light
    }
  }
}
