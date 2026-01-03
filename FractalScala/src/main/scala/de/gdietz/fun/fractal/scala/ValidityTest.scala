/*
 * Projekt: Detact
 *
 * Copyright(c) Symate GmbH. Unerlaubtes Kopieren und Nutzen untersagt.
 */
package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.formula.{ValidityTest => JavaValidityTest}
import de.gdietz.fun.fractal.scala.util.NormedNumber

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

  def normed[X <: NormedNumber](lambda: Double): ValidityTest[X] =
    NormedValidityTest(lambda)

  val invalid: ValidityTest[Any] = InvalidValidityTest


  protected case class NormedValidityTest[X <: NormedNumber](lambda: Double)
    extends ValidityTest[X] {
    private val lambdaSqr: Double = lambda * lambda
    override def isValid(x: X): Boolean = x.normSqr <= lambdaSqr
    override def isSurvivor(x: X): Boolean = false
  }

  protected object InvalidValidityTest extends ValidityTest[Any] with Serializable {
    override def isValid(x: Any): Boolean = false
    override def isSurvivor(x: Any): Boolean = false
  }

}
