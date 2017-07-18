# field-names: simple macro annotation

[![Build Status](https://travis-ci.org/lorandszakacs/field-names.svg?branch=master)](https://travis-ci.org/lorandszakacs/field-names)

`field-names` is a simple scala library—built on top of [scala-meta](https://github.com/scalameta/scalameta)—that provides a simple type annotation called `@FieldNames` which makes the field names of a case class available as runtime `String` values.


## Quickstart:

### sbt

Add the following to your sbt build:
```scala
libraryDependencies += "com.lorandszakacs" %% "field-names" % "0.1.0"
```

Unfortunately you need to also add a dependency both on [scala-meta](https://github.com/scalameta/scalameta), and on the [macro paradise](http://scalameta.org/tutorial/#Setupbuild) compiler-plugin. Include the following settings into your build (N.B. that bellow the settings are only defined, and not used):
```scala
lazy val macroAnnotationSettings = Seq(
  libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0" % Provided,
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full),
  scalacOptions += "-Xplugin-require:macroparadise",
  scalacOptions in(Compile, console) ~= (_ filterNot (_ contains "paradise")) // macroparadise plugin doesn't work in repl yet.
)
```

Example of fully working `sbt` build:
```scala

lazy val root = (project in file(".")).
  settings(
    scalaVersion := "2.12.2", // or "2.11.11"
    libraryDependencies += "com.lorandszakacs" %% "field-names" % "0.1.0"
  ).
  settings(macroAnnotationSettings)


lazy val macroAnnotationSettings = Seq(
  libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0" % Provided,
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full),
  scalacOptions += "-Xplugin-require:macroparadise",
  scalacOptions in(Compile, console) ~= (_ filterNot (_ contains "paradise")) // macroparadise plugin doesn't work in repl yet.
)
```

### Supported Scala versions

## Example:

```scala
import fieldnames.FieldNames

@FieldNames
case class User(
  id: Long,
  name: String,
  email: String,
  username: String
)

object Test extends App {
  if (User.fields.id == "id")
    println(s"yay! we have an ${User.fields.id} field")

  if (User.fields.name == "name")
    println(s"yay! we have an ${User.fields.name} field")

  if (User.fields.email == "email")
    println(s"yay! we have an ${User.fields.id} field")

  if (User.fields.username == "username")
    println(s"yay! we have an ${User.fields.id} field")
}
```

Essentially, it is roughly equivalent to writing something like:
```scala
object User {

  object fields {
    val id: String = "id"
    val name: String = "name"
    val email: String = "email"
    val username: String = "username"
  }

}
```
The macro ensures that _if_ a companion object is explicitly defined, then _all_ pre-existing definitions will be preserved.  

Check out the test to see various scenarios: [src/test/scala/fieldnames/FieldNamesTest.scala](src/test/scala/fieldnames/FieldNamesTest.scala)
