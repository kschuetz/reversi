package reversi.animation

import reversi.core.SquareIndex

sealed trait Animation {
  def startTime: Double

  def duration: Double

  def linearProgress(nowTime: Double): Double

  def hasStarted(nowTime: Double): Boolean = nowTime >= startTime

  def isExpired(nowTime: Double): Boolean

  def isActive(nowTime: Double): Boolean = !isExpired(nowTime)
}

object Animation {

  trait OneTimeAnimation extends Animation {
    def linearProgress(nowTime: Double): Double =
      if nowTime <= startTime then 0.0
      else math.min((nowTime - startTime) / duration, 1.0)

    def isExpired(nowTime: Double): Boolean =
      (nowTime - startTime) >= duration
  }

  trait OneTimeDeferredAnimation extends OneTimeAnimation {
    def startMovingTime: Double

    def endTime: Double

    val duration: Double = endTime - startTime
    val moveDuration: Double = endTime - startMovingTime

    override def linearProgress(nowTime: Double): Double = {
      if moveDuration <= 0 then 1.0
      else if nowTime <= startMovingTime then 0.0
      else math.min((nowTime - startMovingTime) / moveDuration, 1.0)
    }

    override def isExpired(nowTime: Double): Boolean = nowTime >= endTime
  }

  case class FlippingPiece(squareIndex: SquareIndex,
                           startTime: Double,
                           startMovingTime: Double,
                           endTime: Double) extends OneTimeDeferredAnimation
}
