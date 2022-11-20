package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.scala.util.{Complex, Quaternion, Real}

import scala.language.implicitConversions

trait FractalIteratorDefinition[-P] {

  type C

  def apply(c: P, p: P, lambda: Double): FractalInitializedIteratorDefinition[C]

  def validityTest(lambda: Double): ValidityTest[C]

}

object FractalIteratorDefinition {

  def apply[P, X](initializeFunc: (P, P, Double) => FractalInitializedIteratorDefinition[X])
                 (validityTestFunc: Double => ValidityTest[X]): FractalIteratorDefinitionAux[P, X] =
    new FractalIteratorDefinition[P] {
      override type C = X
      override def apply(c: P, p: P, lambda: Double): FractalInitializedIteratorDefinition[C] = initializeFunc(c, p, lambda)
      override def validityTest(lambda: Double): ValidityTest[X] = validityTestFunc(lambda)
    }

  def apply[P, X](z0Func: (P, P) => X)
                 (zNextFunc: (P, P, Double) => X => X)
                 (isValidFunc: Double => X => Boolean): FractalIteratorDefinitionAux[P, X] =
    new FractalIteratorDefinition[P] with Serializable {
      override type C = X
      override def apply(c: P, p: P, lambda: Double): FractalInitializedIteratorDefinition[X] =
        new FractalInitializedIteratorDefinition[X] with Serializable {
          override val z0: X = z0Func(c, p)
          private val zNextInitialized = zNextFunc(c, p, lambda)
          override def zNext(z: X): X = zNextInitialized(z)
        }
      override def validityTest(lambda: Double): ValidityTest[X] =
        new ValidityTest[X] with Serializable {
          private val isValidInitialized = isValidFunc(lambda)
          override def isValid(x: X): Boolean = isValidInitialized(x)
          override def isSurvivor(x: X): Boolean = false
        }
    }


  val invalid: FractalIteratorDefinition[Any] = InvalidFractalIteratorDefinition

  protected object InvalidFractalIteratorDefinition extends FractalIteratorDefinition[Any] {
    override type C = Unit
    override def apply(c: Any, p: Any, lambda: Double): FractalInitializedIteratorDefinition[Unit] = FractalInitializedIteratorDefinition.invalid
    override def validityTest(lambda: Double): ValidityTest[Unit] = ValidityTest.invalid
  }

}

object RealFractalIteratorDefinition {

  def apply[X](initializeFunc: (Real, Real, Double) => FractalInitializedIteratorDefinition[X])
              (validityTestFunc: Double => ValidityTest[X]): RealFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(initializeFunc)(validityTestFunc)

  def apply[X](z0Func: (Real, Real) => X)
              (zNextFunc: (Real, Real, Double) => X => X)
              (isValidFunc: Double => X => Boolean): RealFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(z0Func)(zNextFunc)(isValidFunc)

}

object ComplexFractalIteratorDefinition {

  def apply[X](initializeFunc: (Complex, Complex, Double) => FractalInitializedIteratorDefinition[X])
              (validityTestFunc: Double => ValidityTest[X]): ComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(initializeFunc)(validityTestFunc)

  def apply[X](z0Func: (Complex, Complex) => X)
              (zNextFunc: (Complex, Complex, Double) => X => X)
              (isValidFunc: Double => X => Boolean): ComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(z0Func)(zNextFunc)(isValidFunc)

}

object QuaternionFractalIteratorDefinition {

  def apply[X](initializeFunc: (Quaternion, Quaternion, Double) => FractalInitializedIteratorDefinition[X])
              (validityTestFunc: Double => ValidityTest[X]): QuaternionFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(initializeFunc)(validityTestFunc)

  def apply[X](z0Func: (Quaternion, Quaternion) => X)
              (zNextFunc: (Quaternion, Quaternion, Double) => X => X)
              (isValidFunc: Double => X => Boolean): QuaternionFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(z0Func)(zNextFunc)(isValidFunc)

}
