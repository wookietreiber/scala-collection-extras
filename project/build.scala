import sbt._
import Keys._

object Extras extends Build {
  lazy val root = Project (
    id       = "collection-extras",
    base     = file ("."),
    settings = Defaults.defaultSettings ++ Seq (
      organization       := "com.github.scala-collection-extras",
      version            := "0.1.0-SNAPSHOT",
      scalaVersion       := "2.9.2",
      crossScalaVersions := Seq (
        "2.9.0", "2.9.0-1",
        "2.9.1", "2.9.1-1",
        "2.9.2",
        "2.10.0-M5"
      ),
      scalacOptions <<= (scalaVersion, scalacOptions) map { (sv, opts) ⇒
        if (sv.startsWith("2.10"))
          opts ++ Seq("-language:implicitConversions.", "-language:higherKinds.")
        else
          opts
      },
      initialCommands in (Compile, consoleQuick) <<= initialCommands in Compile,
      initialCommands in Compile in console += """
        import scalay.collection._
      """
    )
  )
}
