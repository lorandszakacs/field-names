# field-names: simple macro annotation

[![Build Status](https://travis-ci.org/lorandszakacs/field-names.svg?branch=master)](https://travis-ci.org/lorandszakacs/field-names)

`field-names` is a simple scala library—built on top of [scala-meta](https://github.com/scalameta/scalameta)—that provides a simple type annotation called `@FieldNames` which makes the field names of a case class available as runtime `String` values.


## Quickstart:

TODO:
add module IDs and stuff like that.


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
```
object User {

  object fields {
    val id: String = "id"
    val name: String = "name"
    val email: String = "email"
    val username: String = "username"
  }

}
```
The macro ensures that if a companion object is explicitly defined, then all pre-existing definitions will be preserved.  

Check out the test to see various scenarios:
