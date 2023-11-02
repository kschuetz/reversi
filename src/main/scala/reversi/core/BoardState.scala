package reversi.core

import reversi.core.Color.{Dark, Light}

trait BoardStateRead {
  def pieces(color: Color): BoardMask

  def occupied: BoardMask
}

class BoardState(private val lightPieces: BoardMask,
                 private val darkPieces: BoardMask) extends BoardStateRead {
  def pieces(color: Color): BoardMask = color match {
    case Light => lightPieces
    case Dark => darkPieces
  }

  def occupied: BoardMask = lightPieces | darkPieces
}
