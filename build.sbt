name := """tw-hooks"""
organization := "dev.mce"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(play.sbt.PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "dev.mce.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "dev.mce.binders._"
