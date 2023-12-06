package reversi.modules

import com.softwaremill.macwire.wire
import reversi.core.{Application, Engine}
import reversi.ui.GameScreen
import reversi.ui.layout.{ScreenLayoutAdapter, ScreenLayoutSettingsProvider}

trait CoreModule(val engine: Engine) {
  def GameScreen: GameScreen

  lazy val application: Application = wire[Application]

  lazy val screenLayoutSettingsProvider: ScreenLayoutSettingsProvider = wire[ScreenLayoutAdapter]
}
