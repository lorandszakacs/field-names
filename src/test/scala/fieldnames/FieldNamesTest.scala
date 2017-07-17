package fieldnames

import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 17 Jul 2017
  *
  */
class FieldNamesTest extends FunSuite {

  test("@FieldNames annotation on case class") {

    @FieldNames
    case class CaseClass(
      id: Int,
      name: String,
      names: Seq[String]
    )

    """CaseClass.fields.id""" should compile
    """CaseClass.fields.name""" should compile
    """CaseClass.fields.names""" should compile
    """val x: String = CaseClass.fields.names""" should compile
  }

  test("@FieldNames annotation on case class with explicit companion object") {

    @FieldNames
    case class CaseClassWC(
      id: Int,
      name: String,
      names: Seq[String]
    )

    object CaseClassWC {
      val PreExistingField: String = "test"
    }

    """CaseClassWC.fields.id""" should compile
    """CaseClassWC.fields.name""" should compile
    """CaseClassWC.fields.names""" should compile
    """CaseClassWC.PreExistingField""" should compile
    """CaseClassWC(id = 42, name = "this is a name", names = Seq("n", "m"))""" should compile
    """val x: String = CaseClassWC.fields.names""" should compile
    assert(CaseClassWC.PreExistingField == "test")
  }

  test("@FieldNames annotation on non-case class") {

    @FieldNames
    class SimpleClass(
      id: Int,
      name: String,
      names: Seq[String]
    )

    """SimpleClass.fields.id""" should compile
    """SimpleClass.fields.name""" should compile
    """SimpleClass.fields.names""" should compile
    """val x: String = SimpleClass.fields.names""" should compile
  }

  test("@FieldNames annotation on non-case class with private-ctor") {

    @FieldNames
    class SimpleClassWPrivateCtor private(
      id: Int,
      name: String,
      names: Seq[String]
    )

    """SimpleClassWPrivateCtor.fields.id""" should compile
    """SimpleClassWPrivateCtor.fields.name""" should compile
    """SimpleClassWPrivateCtor.fields.names""" should compile
    """val x: String = SimpleClassWPrivateCtor.fields.names""" should compile
  }

  test("fail compilation w/ @FieldNames annotation on object") {
    """
      |@FieldNames
      |object Test{}
    """ shouldNot compile
  }

  test("fail compilation w/ @FieldNames annotation on field") {
    """
      |    class SimpleClassWPrivateCtor private(
      |      @FieldNames id: Int,
      |      name: String,
      |      names: Seq[String]
      |    )
    """ shouldNot compile
  }

  test("fail compilation w/ @FieldNames annotation on random code block") {
    """
      {
        @FieldNames println("Hello World")
      }

    """ shouldNot compile
  }


}
