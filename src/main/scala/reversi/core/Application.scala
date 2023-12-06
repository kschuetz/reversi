package reversi.core

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.scalajs.dom.UIEvent
import reversi.ui.GameScreen
import reversi.ui.layout.{BrowserEnvironment, ScreenLayoutSettings, ScreenLayoutSettingsProvider}

final class Application(engine: Engine,
                        GameScreen: GameScreen,
                        screenLayoutSettingsProvider: ScreenLayoutSettingsProvider) {
  private var resizeTimeoutHandle: Int = 0

  def start(gameHost: dom.Element, dialogHost: dom.Element): Unit = {
    val initialScreenLayoutSettings = screenLayoutSettingsForBrowserEnvironment()

    val $screenLayoutSettings = Var(initialScreenLayoutSettings)

    dom.window.onresize = _ =>
      handleWindowResize($screenLayoutSettings)

    val $boardRotation = Val(0d)
    render(gameHost, GameScreen($screenLayoutSettings.signal, $boardRotation))
  }

  private def screenLayoutSettingsForBrowserEnvironment(): ScreenLayoutSettings = {
    val viewPortWidth = dom.window.innerWidth
    val viewPortHeight = dom.window.innerHeight
    val environment = BrowserEnvironment(viewPortWidth, viewPortHeight)
    screenLayoutSettingsProvider.getScreenLayoutSettings(environment)
  }

  private def handleWindowResize($screenLayoutSettings: Var[ScreenLayoutSettings]): Unit = {
    def update(): Unit = {
      resizeTimeoutHandle = 0
      val screenLayoutSettings = screenLayoutSettingsForBrowserEnvironment()
      $screenLayoutSettings.set(screenLayoutSettings)
    }

    if (resizeTimeoutHandle != 0) dom.window.clearTimeout(resizeTimeoutHandle)
    resizeTimeoutHandle = dom.window.setTimeout(() => update(), 100)
  }
}
