package reversi.core

final class GameDriver(engine: Engine) {

  def userClickedSquare(squareIndex: SquareIndex): ModelEffect[GameState] =
    ModelEffect.noEffect

}
