package reversi.core

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.scalajs.dom.UIEvent
import reversi.ui.GameScreen
import reversi.ui.layout.{BrowserEnvironment, ScreenLayoutSettings, ScreenLayoutSettingsProvider}
import reversi.ui.models.{Fraction, PieceTransforms}

final class Application(engine: Engine,
                        GameScreen: GameScreen,
                        screenLayoutSettingsProvider: ScreenLayoutSettingsProvider) {
  private var resizeTimeoutHandle: Int = 0

  private val SandboxBoard1 = SquareIndex.All.foldLeft(BoardState.empty) {
    case (acc, idx) =>
      idx.toInt % 3 match {
        case 0 => acc.set(idx, Color.Dark)
        case 1 => acc.set(idx, Color.Light)
        case _ => acc
      }
  }

  private val SandboxPieceTransforms1 = SquareIndex.All.foldLeft(PieceTransforms.none) {
    case (acc, idx) =>
      acc.setFlipPosition(idx, Fraction(idx.toInt / 64.0))
  }

  def start(gameHost: dom.Element, dialogHost: dom.Element): Unit = {
    val initialScreenLayoutSettings = screenLayoutSettingsForBrowserEnvironment()

    val $screenLayoutSettings = Var(initialScreenLayoutSettings)

    dom.window.onresize = _ =>
      handleWindowResize($screenLayoutSettings)

    val $boardRotation = Val(0d)
    val $boardState = Var(SandboxBoard1)
    val $pieceTransforms = Var(SandboxPieceTransforms1)
    val $turnToPlay = Var(Color.Dark)
    val screen = GameScreen($screenLayoutSettings.signal, $boardRotation, $boardState.signal, $pieceTransforms.signal,
      $turnToPlay.signal)
    render(gameHost, screen)
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
