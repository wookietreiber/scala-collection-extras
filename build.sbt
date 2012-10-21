
name := "scala-extras"

organization := "com.github.scala-extras"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.0-RC1"

initialCommands in (Compile, consoleQuick) <<= initialCommands in Compile

initialCommands in Compile in console += """
  import scalax.extras._
"""

