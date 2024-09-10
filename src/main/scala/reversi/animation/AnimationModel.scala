package reversi.animation

case class AnimationModel(play: List[Animation]) {
  def hasActivePlayAnimations(nowTime: Instant): Boolean =
    play.exists(_.isActive(nowTime))

  def updateNowTime(newTime: Instant): AnimationModel = {
    val newPlay = play.filterNot(_.isExpired(newTime))
    AnimationModel(newPlay)
  }
}
