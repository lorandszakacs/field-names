package fieldnames

import scala.annotation.StaticAnnotation
import scala.meta._

/**
  *
  * Usage:
  *
  * {{{
  *   @FieldNames
  *   case class Repr(
  *     id: String
  *   )
  * }}}
  * Adds an object called `fields` to the companion object of the
  * annotated class which contains variables named after the fields
  * with their names as string values. i.e.
  * {{{
  *   object Repr {
  *     object fields {
  *       val id: String = "id"
  *     }
  *   }
  * }}}
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com
  * @since 17 Jul 2017
  *
  */
class FieldNames extends StaticAnnotation {

  inline def apply(defn: Any): Any = meta {
    val (clz, companion) = defn match {
      case q"${cls: Defn.Class}; ${companion: Defn.Object}" => (cls, companion)
      case cls: Defn.Class => (cls, q"object ${Term.Name(cls.name.value)}")
      case _ => abort("@FieldNames must annotate a case class or class")
    }

    val nameConstantVals =
      clz.ctor.paramss.flatten.map { p =>
        q"""val ${Pat.Var.Term(Term.Name(p.name.value))} : String = ${Lit.String(p.name.value)} """
      }

    val fieldsObject =
      q"""
         object fields {
            ..$nameConstantVals
         }
       """

    val newCompanion = companion.copy(
      templ = companion.templ.copy(
        stats = Some(companion.templ.stats.getOrElse(Nil) :+ fieldsObject)
      )
    )

    q"$clz; $newCompanion"
  }


}


