package reversi.core

import com.raquo.laminar.api.L.{svg, *, given}
import reversi.core.ModelEffectResult.noChange
import reversi.logger
import reversi.ui.models.SquareInteraction


final class GameDriver(engine: Engine) {

  def squareInteractionsObserver($gameState: Var[GameState],
                                 $mouseInSquare: Var[Option[SquareIndex]]): Observer[SquareInteraction] =
    Observer[SquareInteraction] {
      case SquareInteraction.MouseOver(square) => $mouseInSquare.update(_ => Some(square))
      case SquareInteraction.MouseOut(square) => $mouseInSquare.update { current =>
        if current.contains(square) then None else current
      }
      case SquareInteraction.Click(square) =>
        logger.inputEvents.info(s"Clicked $square")
        $gameState.update { stateIn =>
          userClickedSquare(square).run(stateIn) match {
            case ModelEffectResult.NoChange => stateIn
            case ModelEffectResult.Changed(result) => result
          }
        }
    }

  private def userClickedSquare(squareIndex: SquareIndex): ModelEffect[GameState] = ModelEffect { stateIn =>
    stateIn.turnToPlay.fold(noChange) { turnToPlay =>
      if stateIn.readyForInput && stateIn.legalMoves.contains(squareIndex) then {
        logger.inputEvents.info(s"Playing $turnToPlay on $squareIndex")
        noChange
      } else noChange
    }
  }

}
