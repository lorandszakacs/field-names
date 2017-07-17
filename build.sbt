import sbt.Keys._

lazy val root = Project("field-names", file(".")).settings(
  scalaVersion := "2.12.2",
  libraryDependencies ++= Seq(
    "org.scalameta" %% "scalameta" % "1.8.0" % Provided,
    "org.scalatest" %% "scalatest" % "3.0.1" % Test
  ),
  macroAnnotationSettings
)

lazy val macroAnnotationSettings = Seq(
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full),
  scalacOptions += "-Xplugin-require:macroparadise",
  scalacOptions in(Compile, console) ~= (_ filterNot (_ contains "paradise")) // macroparadise plugin doesn't work in repl yet.
)
