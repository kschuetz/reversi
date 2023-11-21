package reversi.ui.layout

case class SideChromeLayoutSettings(width: Pixels = Pixels(200),
                                    padding: Pixels = Pixels(6),
                                    buttonPaddingX: Pixels = Pixels(10),
                                    buttonPaddingY: Pixels = Pixels(12),
                                    buttonAreaPaddingY: Pixels = Pixels(14),
                                    buttonHeight: Pixels = Pixels(48),
                                    powerMeterHeight: Pixels = Pixels(30),
                                    powerMeterWidth: Pixels = Pixels(170))

case class TopChromeLayoutSettings(height: Pixels = Pixels(90),
                                   padding: Pixels = Pixels(6))

case class GameOverPanelLayoutSettings(width: Pixels = Pixels(400),
                                       height: Pixels = Pixels(200))

case class ScreenLayoutSettings(gameSceneWidth: Pixels = Pixels(800),
                                gameSceneHeight: Pixels = Pixels(800),
                                topChrome: TopChromeLayoutSettings = TopChromeLayoutSettings(),
                                sideChrome: SideChromeLayoutSettings = SideChromeLayoutSettings(),
                                gameOverPanel: GameOverPanelLayoutSettings = GameOverPanelLayoutSettings())

trait ScreenLayoutSettingsProvider {
  def getScreenLayoutSettings(viewPortInfo: ViewPortInfo): ScreenLayoutSettings
}

case class ConstantScreenLayoutSettings(settings: ScreenLayoutSettings) extends ScreenLayoutSettingsProvider {
  def getScreenLayoutSettings(viewPortInfo: ViewPortInfo): ScreenLayoutSettings = settings
}
