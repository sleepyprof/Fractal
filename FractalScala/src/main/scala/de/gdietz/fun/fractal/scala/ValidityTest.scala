/*
 * Projekt: Detact
 *
 * Copyright(c) Symate GmbH. Unerlaubtes Kopieren und Nutzen untersagt.
 */
package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.formula.{ValidityTest => JavaValidityTest}

trait ValidityTest[-C] {
  self =>

  def isValid(x: C): Boolean

  def isSurvivor(x: C): Boolean

  def toJavaValidityTest[X <: C]: JavaValidityTest[X] =
    new JavaValidityTest[X] with Serializable {
      override def isValid(x: X): Boolean = self.isValid(x)
      override def isSurvivor(x: X): Boolean = self.isSurvivor(x)
    }

}

object ValidityTest {

  val invalid: ValidityTest[Any] = InvalidValidityTest

  protected object InvalidValidityTest extends ValidityTest[Any] {
    override def isValid(x: Any): Boolean = false
    override def isSurvivor(x: Any): Boolean = false
  }

}
