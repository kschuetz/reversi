import org.scalajs.linker.interface.ModuleSplitStyle

val publicDev = taskKey[String]("output directory for `npm run dev`")
val publicProd = taskKey[String]("output directory for `npm run build`")

// use eliding to drop some debug code in the production build
lazy val elideOptions = settingKey[Seq[String]]("Set limit for elidable functions")

lazy val `reversi` = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "reversi",
    version := Settings.version,
    scalaVersion := Settings.versions.scala,
    elideOptions := {
      // build-mode is passed in from Vite using -Dbuild-mode option. See vite.config.js.
      // This is a hack. Do it differently if there is ever a better way in SBT.
      Option(System.getProperty("build-mode")) match {
        case Some("prod") => elideBelowWarning
        case _ => Seq()
      }
    },
    scalacOptions ++= (Settings.scalacOptions ++ elideOptions.value),

    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("reversi")))
    },
    libraryDependencies ++= Seq(
      "com.raquo" %%% "laminar" % Settings.versions.laminar,
      "org.scalatest" %%% "scalatest" % Settings.versions.scalaTest % "test",
      "com.softwaremill.macwire" %% "macros" % Settings.versions.macwire % Provided,
    ),

    // use uTest framework for tests
    //    testFrameworks += new TestFramework("utest.runner.Framework"),

    publicDev := linkerOutputDirectory((Compile / fastLinkJS).value).getAbsolutePath,
    publicProd := linkerOutputDirectory((Compile / fullLinkJS).value).getAbsolutePath,
  )

def linkerOutputDirectory(v: Attributed[org.scalajs.linker.interface.Report]): File = {
  v.get(scalaJSLinkerOutputDirectory.key).getOrElse {
    throw new MessageOnlyException(
      "Linking report was not attributed with output directory. " +
        "Please report this as a Scala.js bug.")
  }
}

lazy val elideBelowWarning = Seq("-Xelide-below", "WARNING")
reversi / libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "latest.integration" % Test
)
