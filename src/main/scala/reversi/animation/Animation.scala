package reversi.animation

import reversi.core.SquareIndex

sealed trait Animation {
  def startTime: Instant

  def duration: Millis

  def linearProgress(nowTime: Instant): Double

  def hasStarted(nowTime: Instant): Boolean = nowTime >= startTime

  def isExpired(nowTime: Instant): Boolean

  def isActive(nowTime: Instant): Boolean = !isExpired(nowTime)
}

object Animation {

  trait OneTimeAnimation extends Animation {
    def linearProgress(nowTime: Instant): Double =
      if nowTime <= startTime then 0.0
      else math.min((nowTime - startTime) / duration, 1.0)

    def isExpired(nowTime: Instant): Boolean =
      (nowTime - startTime) >= duration
  }

  trait OneTimeDeferredAnimation extends OneTimeAnimation {
    def startMovingTime: Instant

    def endTime: Instant

    val duration: Millis = endTime - startTime
    val moveDuration: Millis = endTime - startMovingTime

    override def linearProgress(nowTime: Instant): Double = {
      if moveDuration <= Millis(0) then 1.0
      else if nowTime <= startMovingTime then 0.0
      else math.min((nowTime - startMovingTime) / moveDuration, 1.0)
    }

    override def isExpired(nowTime: Instant): Boolean = nowTime >= endTime
  }

  case class FlippingPiece(squareIndex: SquareIndex,
                           startTime: Instant,
                           startMovingTime: Instant,
                           endTime: Instant) extends OneTimeDeferredAnimation
}
