package reversi.modules

import com.softwaremill.macwire.wire
import reversi.ui.board.PhysicalBoard
import reversi.ui.chrome.{PowerMeter, SideChrome, TopChrome}
import reversi.ui.{DynamicScene, GameScreen, SceneContainer, SceneFrame}

trait UserInterfaceModule {
  lazy val dynamicScene: DynamicScene = wire[DynamicScene]

  lazy val gameScreen: GameScreen = wire[GameScreen]

  lazy val physicalBoard: PhysicalBoard = wire[PhysicalBoard]

  lazy val powerMeter: PowerMeter = wire[PowerMeter]

  lazy val topChrome: TopChrome = wire[TopChrome]

  lazy val sceneContainer: SceneContainer = wire[SceneContainer]

  lazy val sceneFrame: SceneFrame = wire[SceneFrame]

  lazy val sideChrome: SideChrome = wire[SideChrome]

}
