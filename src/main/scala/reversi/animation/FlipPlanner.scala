package reversi.animation

import reversi.core.{ModelEffect, SquareIndex}

final class FlipPlanner(animationSettings: AnimationSettings,
                        clock: Clock) {
  def scheduleFlips(squares: Set[SquareIndex]): ModelEffect[AnimationModel] =
    ModelEffect.noEffect
}
