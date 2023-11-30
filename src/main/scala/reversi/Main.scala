package reversi

import org.scalajs.dom
import org.scalajs.dom.{Event, document}
import reversi.core.*
import reversi.logger.*
import reversi.modules.{BasicsModule, CoreModule, UserInterfaceModule}

import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Main")
object Main {

  @JSExport
  def main(args: Array[String]): Unit = {
    val wasmPromise = js.Dynamic.global.window._reversiWasm.asInstanceOf[Promise[js.Dynamic]]

    dom.document.addEventListener[Event]("DOMContentLoaded", (event: Event) => {
      log.info("Application starting")
      val host = dom.document.getElementById("root")
      val dialogHost = dom.document.getElementById("dialog-root")
      wasmPromise
        .`then`(result => {
          log.info("Engine loaded")

          val engineApi = result.exports.asInstanceOf[EngineApi]
          val engine = new Engine(engineApi)
          bootstrap(host, dialogHost, engine)
        })
    })
  }

  private def bootstrap(host: dom.Element, dialogHost: dom.Element, engine: Engine): Unit = {
    val module = new BasicsModule with CoreModule(engine) with UserInterfaceModule
    val application = module.application
    application.start(host, dialogHost)
  }

}
