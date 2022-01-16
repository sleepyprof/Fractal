package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.scala.util.Complex

import scala.language.implicitConversions

trait FractalIteratorDefinition {

  type C

  def z0(c: Complex, p: Complex): C

  def zNext(c: Complex, p: Complex): C => C

  def isValid: C => Boolean

}

object FractalIteratorDefinition {

  val invalid: FractalIteratorDefinition = InvalidFractalIteratorDefinition

  protected object InvalidFractalIteratorDefinition extends FractalIteratorDefinition {
    override type C = Unit
    override def z0(c: Complex, p: Complex): Unit = ()
    private val identity0: Unit => Unit = identity
    override def zNext(c: Complex, p: Complex): Unit => Unit = identity0
    override val isValid: Unit => Boolean = _ => false
  }


  implicit def fractalIteratorFunctionsToFractalIteratorDefinition[X](definition: ((Complex, Complex) => (X, X => X), X => Boolean)): FractalIteratorDefinition =
    new FractalIteratorDefinition {
      override type C = X
      override def z0(c: Complex, p: Complex): X = definition._1(c, p)._1
      override def zNext(c: Complex, p: Complex): X => X = definition._1(c, p)._2
      override val isValid: X => Boolean = definition._2
    }

}
