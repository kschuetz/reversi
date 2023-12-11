package reversi.modules

import com.softwaremill.macwire.wire
import reversi.ui.board.PhysicalBoard
import reversi.ui.chrome.*
import reversi.ui.piece.PhysicalPiece
import reversi.ui.{DynamicScene, GameScreen, SceneContainer, SceneFrame}

trait UserInterfaceModule {
  lazy val DynamicScene: DynamicScene = wire[DynamicScene]

  lazy val GameScreen: GameScreen = wire[GameScreen]

  lazy val PieceAvatar: PieceAvatar = wire[PieceAvatar]

  lazy val PhysicalBoard: PhysicalBoard = wire[PhysicalBoard]

  lazy val PhysicalPiece: PhysicalPiece = wire[PhysicalPiece]

  lazy val PlayerPanel: PlayerPanel = wire[PlayerPanel]

  lazy val PowerMeter: PowerMeter = wire[PowerMeter]

  lazy val TopChrome: TopChrome = wire[TopChrome]

  lazy val SceneContainer: SceneContainer = wire[SceneContainer]

  lazy val SceneFrame: SceneFrame = wire[SceneFrame]

  lazy val SideChrome: SideChrome = wire[SideChrome]

}
