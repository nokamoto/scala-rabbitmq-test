import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._

object Build extends sbt.Build {
  lazy val root = (project in file(".")).settings(
    name := "scala-rabbitmq-test",
    scalaVersion := "2.11.8",
    assemblyJarName in assembly := "scala-rabbitmq-test.jar",
    libraryDependencies += "com.rabbitmq" % "amqp-client" % "3.6.1")
}
