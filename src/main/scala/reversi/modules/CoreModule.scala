package reversi.modules

import com.softwaremill.macwire.wire
import reversi.core.{Application, Engine}
import reversi.ui.GameScreen

trait CoreModule(val engine: Engine) {
  protected def gameScreen: GameScreen

  lazy val application: Application = wire[Application]
}
