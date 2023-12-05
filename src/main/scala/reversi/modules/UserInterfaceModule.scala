package reversi.modules

import com.softwaremill.macwire.wire
import reversi.ui.board.PhysicalBoard
import reversi.ui.chrome.{PlayerPanel, PowerMeter, SideChrome, TopChrome}
import reversi.ui.{DynamicScene, GameScreen, SceneContainer, SceneFrame}

trait UserInterfaceModule {
  lazy val DynamicScene: DynamicScene = wire[DynamicScene]

  lazy val GameScreen: GameScreen = wire[GameScreen]

  lazy val PhysicalBoard: PhysicalBoard = wire[PhysicalBoard]

  lazy val PlayerPanel: PlayerPanel = wire[PlayerPanel]

  lazy val PowerMeter: PowerMeter = wire[PowerMeter]

  lazy val TopChrome: TopChrome = wire[TopChrome]

  lazy val SceneContainer: SceneContainer = wire[SceneContainer]

  lazy val SceneFrame: SceneFrame = wire[SceneFrame]

  lazy val SideChrome: SideChrome = wire[SideChrome]

}
