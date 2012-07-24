import sbt._
import Keys._

object Util extends Build {
  lazy val root = Project ( "par-merge-sort-shuffle", file ("."),
    settings = Defaults.defaultSettings ++ Seq (
      organization         := "com.github.par-merge-sort-shuffle",
      version              := "0.1.0-SNAPSHOT",
      scalaVersion         := "2.10.0-M5",
      crossScalaVersions   := Seq (
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
        import scalax.util._
      """
    )
  )
}
