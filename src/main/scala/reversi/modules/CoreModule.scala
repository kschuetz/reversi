package reversi.modules

import com.softwaremill.macwire.wire
import reversi.core.{Application, Engine}
import reversi.ui.GameScreen

trait CoreModule(val engine: Engine) {
  def GameScreen: GameScreen

  lazy val application: Application = wire[Application]
}
