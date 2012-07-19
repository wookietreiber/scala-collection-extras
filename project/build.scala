import sbt._
import Keys._

object Util extends Build {
  lazy val root = Project ( "par-merge-sort-shuffle", file ("."),
    settings = Defaults.defaultSettings ++ Seq (
      organization         := "com.github.par-merge-sort-shuffle",
      version              := "0.1.0-SNAPSHOT",
      scalaVersion         := "2.10.0-M5",
      initialCommands in (Compile, consoleQuick) <<= initialCommands in Compile,
      initialCommands in Compile in console += """
        import scalax.util._
      """
    )
  )
}
