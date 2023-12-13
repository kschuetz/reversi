package reversi.core

import com.raquo.laminar.api.L.{EventBus, Var}
import reversi.ui.models.{PieceTransforms, SquareInteraction}

final class ApplicationModel(val $gameState: Var[GameState],
                             val $pieceTransforms: Var[PieceTransforms],
                             val squareInteractions: EventBus[SquareInteraction],
                             val $mouseInSquare: Var[Option[SquareIndex]],
                             val $boardRotation: Var[Double])
                            
