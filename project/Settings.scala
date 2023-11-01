
object Settings {
  val version = "0.1.0-SNAPSHOT"

  val scalacOptions: Seq[String] = Seq(
    "-unchecked",
    "-encoding",
    "utf-8",
    "-deprecation",
    "-feature"
  )

  object versions {
    val laminar = "16.0.0"
    val scala = "3.3.1"
    val scalaTest = "3.2.17"
  }

}
