package reversi.core

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import reversi.ui.GameScreen
import reversi.ui.layout.ScreenLayoutSettings

class Application(engine: Engine,
                  GameScreen: GameScreen) {
  def start(gameHost: dom.Element, dialogHost: dom.Element): Unit = {
    val $screenLayoutSettings = Var(ScreenLayoutSettings()).signal
    val $boardRotation = Val(0d)
    render(gameHost, GameScreen($screenLayoutSettings, $boardRotation))
  }
}
