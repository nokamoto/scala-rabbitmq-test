import sbt.Keys._
import sbt._

object Build extends sbt.Build {
  lazy val root = (project in file(".")).settings(
    name := "scala-rabbitmq-test",
    scalaVersion := "2.11.8",
    libraryDependencies += "com.rabbitmq" % "amqp-client" % "3.6.1")
}
