package reversi.animation

import org.scalajs.dom.window.performance

trait Clock {
  def now(): Instant
}

final class DefaultClock extends Clock {
  def now(): Instant = Instant(performance.now())
}
