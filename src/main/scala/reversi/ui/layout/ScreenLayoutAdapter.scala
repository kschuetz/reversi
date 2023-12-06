package reversi.ui.layout

import reversi.logger.*

final class ScreenLayoutAdapter extends ScreenLayoutSettingsProvider {

  private case class CacheSettings(width: Pixels,
                                   height: Pixels,
                                   settings: ScreenLayoutSettings)

  private var cache: CacheSettings = _

  private val thresholds = Vector(
    (Pixels(1024), Pixels(896)),
    (Pixels(914), Pixels(786)),
    (Pixels(804), Pixels(676)),
    (Pixels(694), Pixels(566)))

  private val Large = ScreenLayoutSettings()
  private val MediumLarge = Large.copy(
    gameSceneWidth = Pixels(700),
    gameSceneHeight = Pixels(700),
    topChrome = Large.topChrome.copy(height = Pixels(80)),
    sideChrome = Large.sideChrome.copy(width = Pixels(190),
      buttonHeight = Pixels(42),
      powerMeterWidth = Pixels(172),
      powerMeterHeight = Pixels(28))
  )
  private val Medium = Large.copy(
    gameSceneWidth = Pixels(600),
    gameSceneHeight = Pixels(600),
    topChrome = Large.topChrome.copy(height = Pixels(70)),
    sideChrome = Large.sideChrome.copy(width = Pixels(180),
      buttonHeight = Pixels(36),
      powerMeterWidth = Pixels(162),
      powerMeterHeight = Pixels(26))
  )
  private val MediumSmall = Large.copy(
    gameSceneWidth = Pixels(500),
    gameSceneHeight = Pixels(500),
    topChrome = Large.topChrome.copy(height = Pixels(60)),
    sideChrome = Large.sideChrome.copy(width = Pixels(170),
      buttonHeight = Pixels(30),
      powerMeterWidth = Pixels(152),
      powerMeterHeight = Pixels(23))
  )
  private val Small = Large.copy(
    gameSceneWidth = Pixels(400),
    gameSceneHeight = Pixels(400),
    topChrome = Large.topChrome.copy(height = Pixels(50)),
    sideChrome = Large.sideChrome.copy(width = Pixels(160),
      buttonHeight = Pixels(24),
      powerMeterWidth = Pixels(144),
      powerMeterHeight = Pixels(23))
  )

  private lazy val levels = Vector(
    Large,
    MediumLarge,
    Medium,
    MediumSmall,
  ).lift

  private def getSettings(width: Pixels, height: Pixels): ScreenLayoutSettings = {
    val idx = thresholds.indexWhere { case (minWidth, minHeight) =>
      width >= minWidth && height >= minHeight
    }
    levels(idx).getOrElse(Small)
  }

  override def getScreenLayoutSettings(viewPortInfo: ViewPortInfo): ScreenLayoutSettings = {
    val width = Pixels(viewPortInfo.viewPortWidth.toInt)
    val height = Pixels(viewPortInfo.viewPortHeight.toInt)

    if (cache != null && cache.width == width && cache.height == height) cache.settings
    else {
      val newSettings = getSettings(width, height)

      log.info(s"Adapting screen layout for dimensions ($width × $height)")

      cache = CacheSettings(width, height, newSettings)
      newSettings
    }
  }
}
