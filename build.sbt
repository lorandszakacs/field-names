/**
  * Copyright 2017 Loránd Szakács
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  *
  */
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
