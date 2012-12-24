
name := "scala-extras"

organization := "com.github.wookietreiber.scala-extras"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.0"

initialCommands in (Compile, consoleQuick) <<= initialCommands in Compile

initialCommands in Compile in console += """
  import scalax.extras._
"""

