package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.scala.util.{BigComplex, Complex, HigherNumber, HigherVector, NormedNumber, OptBigComplex, OptComplex, OptHigherNumber, OptNormedNumber, OptQuaternion, OptReal, Quaternion, Real, Vector3D}

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

  def create[P, X](z0Func: (P, P) => X)
                  (zNextFunc: (P, P, Double) => X => X)
                  (isValidFunc: Double => X => Boolean): FractalIteratorDefinitionAux[P, X] =
    new FractalIteratorDefinition[P] with Serializable {
      override type C = X
      override def apply(c: P, p: P, lambda: Double): FractalInitializedIteratorDefinition[X] =
        new FractalInitializedIteratorDefinition[X] with Serializable {
          override val z0: X = z0Func(c, p)
          private val zNextInitialized: X => X = zNextFunc(c, p, lambda)
          override def zNext(z: X): X = zNextInitialized(z)
        }
      override def validityTest(lambda: Double): ValidityTest[X] =
        new ValidityTest[X] with Serializable {
          private val isValidInitialized: X => Boolean = isValidFunc(lambda)
          override def isValid(x: X): Boolean = isValidInitialized(x)
          override def isSurvivor(x: X): Boolean = false
        }
    }

  def normed[P, X <: NormedNumber](z0Func: (P, P) => X)
                                  (zNextFunc: (P, P) => X => X): FractalIteratorDefinitionAux[P, X] =
    new FractalIteratorDefinition[P] with Serializable {
      override type C = X
      override def apply(c: P, p: P, lambda: Double): FractalInitializedIteratorDefinition[X] =
        new FractalInitializedIteratorDefinition[X] with Serializable {
          override val z0: X = z0Func(c, p)
          private val zNextInitialized: X => X = zNextFunc(c, p)
          override def zNext(z: X): X = zNextInitialized(z)
        }
      override def validityTest(lambda: Double): ValidityTest[X] =
        new ValidityTest[X] with Serializable {
          private val lambdaSqr: Double = lambda * lambda
          override def isValid(x: X): Boolean = x.normSqr <= lambdaSqr
          override def isSurvivor(x: X): Boolean = false
        }
    }

  def optNormed[P, X <: OptNormedNumber](z0Func: (P, P) => X)
                                        (zNextFunc: (P, P) => X => X): FractalIteratorDefinitionAux[P, X] =
    new FractalIteratorDefinition[P] with Serializable {
      override type C = X
      override def apply(c: P, p: P, lambda: Double): FractalInitializedIteratorDefinition[X] =
        new FractalInitializedIteratorDefinition[X] with Serializable {
          override val z0: X = z0Func(c, p)
          private val zNextInitialized: X => X = zNextFunc(c, p)
          override def zNext(z: X): X = zNextInitialized(z)
        }

      override def validityTest(lambda: Double): ValidityTest[X] =
        new ValidityTest[X] with Serializable {
          private val lambdaSqr: Double = lambda * lambda
          override def isValid(x: X): Boolean = x.optNormSqr.exists(_ <= lambdaSqr)
          override def isSurvivor(x: X): Boolean = false
        }
    }

  def any[P, V <: HigherVector[V, O, X], O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](z0Func: (P, P) => V)
                                                                                                 (zNextFunc: (P, P) => V => V): FractalIteratorDefinitionAux[P, V] =
    new FractalIteratorDefinition[P] with Serializable {
      override type C = V
      override def apply(c: P, p: P, lambda: Double): FractalInitializedIteratorDefinition[V] =
        new FractalInitializedIteratorDefinition[V] with Serializable {
          private val lambdaSqr: Double = lambda * lambda
          override val z0: V = z0Func(c, p)
          private val zNextInitialized: V => V = zNextFunc(c, p)
          override def zNext(z: V): V = zNextInitialized(z).filterNumber(_.normSqr <= lambdaSqr)
        }
      override def validityTest(lambda: Double): ValidityTest[V] =
        new ValidityTest[V] with Serializable {
          override def isValid(x: V): Boolean = x.existsNumber
          override def isSurvivor(x: V): Boolean = false
        }
    }

  def all[P, V <: HigherVector[V, O, X], O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](z0Func: (P, P) => V)
                                                                                                 (zNextFunc: (P, P) => V => V): FractalIteratorDefinitionAux[P, V] =
    new FractalIteratorDefinition[P] with Serializable {
      override type C = V
      override def apply(c: P, p: P, lambda: Double): FractalInitializedIteratorDefinition[V] =
        new FractalInitializedIteratorDefinition[V] with Serializable {
          private val lambdaSqr: Double = lambda * lambda
          override val z0: V = z0Func(c, p)
          private val zNextInitialized: V => V = zNextFunc(c, p)
          override def zNext(z: V): V = zNextInitialized(z).filterNumber(_.normSqr <= lambdaSqr)
        }
      override def validityTest(lambda: Double): ValidityTest[V] =
        new ValidityTest[V] with Serializable {
          override def isValid(x: V): Boolean = x.isAllNumber
          override def isSurvivor(x: V): Boolean = false
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

  def create[X](z0Func: (Real, Real) => X)
               (zNextFunc: (Real, Real, Double) => X => X)
               (isValidFunc: Double => X => Boolean): RealFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.create(z0Func)(zNextFunc)(isValidFunc)

  def normed[X <: NormedNumber](z0Func: (Real, Real) => X)
                               (zNextFunc: (Real, Real) => X => X): RealFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.normed(z0Func)(zNextFunc)

  def optNormed[X <: OptNormedNumber](z0Func: (Real, Real) => X)
                                     (zNextFunc: (Real, Real) => X => X): RealFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.optNormed(z0Func)(zNextFunc)

  def any[X <: HigherVector[X, OptReal, Real]](z0Func: (Real, Real) => X)
                                              (zNextFunc: (Real, Real) => X => X): RealFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.any[Real, X, OptReal, Real](z0Func)(zNextFunc)

  def all[X <: HigherVector[X, OptReal, Real]](z0Func: (Real, Real) => X)
                                              (zNextFunc: (Real, Real) => X => X): RealFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.all[Real, X, OptReal, Real](z0Func)(zNextFunc)

}

object ComplexFractalIteratorDefinition {

  def apply[X](initializeFunc: (Complex, Complex, Double) => FractalInitializedIteratorDefinition[X])
              (validityTestFunc: Double => ValidityTest[X]): ComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(initializeFunc)(validityTestFunc)

  def create[X](z0Func: (Complex, Complex) => X)
               (zNextFunc: (Complex, Complex, Double) => X => X)
               (isValidFunc: Double => X => Boolean): ComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.create(z0Func)(zNextFunc)(isValidFunc)

  def normed[X <: NormedNumber](z0Func: (Complex, Complex) => X)
                               (zNextFunc: (Complex, Complex) => X => X): ComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.normed(z0Func)(zNextFunc)

  def optNormed[X <: OptNormedNumber](z0Func: (Complex, Complex) => X)
                                     (zNextFunc: (Complex, Complex) => X => X): ComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.optNormed(z0Func)(zNextFunc)

  def any[X <: HigherVector[X, OptComplex, Complex]](z0Func: (Complex, Complex) => X)
                                                    (zNextFunc: (Complex, Complex) => X => X): ComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.any[Complex, X, OptComplex, Complex](z0Func)(zNextFunc)

  def all[X <: HigherVector[X, OptComplex, Complex]](z0Func: (Complex, Complex) => X)
                                                    (zNextFunc: (Complex, Complex) => X => X): ComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.all[Complex, X, OptComplex, Complex](z0Func)(zNextFunc)

}

object QuaternionFractalIteratorDefinition {

  def apply[X](initializeFunc: (Quaternion, Quaternion, Double) => FractalInitializedIteratorDefinition[X])
              (validityTestFunc: Double => ValidityTest[X]): QuaternionFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(initializeFunc)(validityTestFunc)

  def create[X](z0Func: (Quaternion, Quaternion) => X)
               (zNextFunc: (Quaternion, Quaternion, Double) => X => X)
               (isValidFunc: Double => X => Boolean): QuaternionFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.create(z0Func)(zNextFunc)(isValidFunc)

  def normed[X <: NormedNumber](z0Func: (Quaternion, Quaternion) => X)
                               (zNextFunc: (Quaternion, Quaternion) => X => X): QuaternionFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.normed(z0Func)(zNextFunc)

  def optNormed[X <: OptNormedNumber](z0Func: (Quaternion, Quaternion) => X)
                                     (zNextFunc: (Quaternion, Quaternion) => X => X): QuaternionFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.optNormed(z0Func)(zNextFunc)

  def any[X <: HigherVector[X, OptQuaternion, Quaternion]](z0Func: (Quaternion, Quaternion) => X)
                                                          (zNextFunc: (Quaternion, Quaternion) => X => X): QuaternionFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.any[Quaternion, X, OptQuaternion, Quaternion](z0Func)(zNextFunc)

  def all[X <: HigherVector[X, OptQuaternion, Quaternion]](z0Func: (Quaternion, Quaternion) => X)
                                                          (zNextFunc: (Quaternion, Quaternion) => X => X): QuaternionFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.all[Quaternion, X, OptQuaternion, Quaternion](z0Func)(zNextFunc)

}

object Vector3DFractalIteratorDefinition {

  def apply[X](initializeFunc: (Vector3D, Vector3D, Double) => FractalInitializedIteratorDefinition[X])
              (validityTestFunc: Double => ValidityTest[X]): Vector3DFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(initializeFunc)(validityTestFunc)

  def create[X](z0Func: (Vector3D, Vector3D) => X)
               (zNextFunc: (Vector3D, Vector3D, Double) => X => X)
               (isValidFunc: Double => X => Boolean): Vector3DFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.create(z0Func)(zNextFunc)(isValidFunc)

  def normed[X <: NormedNumber](z0Func: (Vector3D, Vector3D) => X)
                               (zNextFunc: (Vector3D, Vector3D) => X => X): Vector3DFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.normed(z0Func)(zNextFunc)

  def optNormed[X <: OptNormedNumber](z0Func: (Vector3D, Vector3D) => X)
                                     (zNextFunc: (Vector3D, Vector3D) => X => X): Vector3DFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.optNormed(z0Func)(zNextFunc)

  def anyComplex[X <: HigherVector[X, OptComplex, Complex]](z0Func: (Vector3D, Vector3D) => X)
                                                           (zNextFunc: (Vector3D, Vector3D) => X => X): Vector3DFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.any[Vector3D, X, OptComplex, Complex](z0Func)(zNextFunc)

  def anyQuaternion[X <: HigherVector[X, OptQuaternion, Quaternion]](z0Func: (Vector3D, Vector3D) => X)
                                                                    (zNextFunc: (Vector3D, Vector3D) => X => X): Vector3DFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.any[Vector3D, X, OptQuaternion, Quaternion](z0Func)(zNextFunc)

  def allComplex[X <: HigherVector[X, OptComplex, Complex]](z0Func: (Vector3D, Vector3D) => X)
                                                           (zNextFunc: (Vector3D, Vector3D) => X => X): Vector3DFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.all[Vector3D, X, OptComplex, Complex](z0Func)(zNextFunc)

  def allQuaternion[X <: HigherVector[X, OptQuaternion, Quaternion]](z0Func: (Vector3D, Vector3D) => X)
                                                                    (zNextFunc: (Vector3D, Vector3D) => X => X): Vector3DFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.all[Vector3D, X, OptQuaternion, Quaternion](z0Func)(zNextFunc)

}

object BigComplexFractalIteratorDefinition {

  def apply[X](initializeFunc: (BigComplex, BigComplex, Double) => FractalInitializedIteratorDefinition[X])
              (validityTestFunc: Double => ValidityTest[X]): BigComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition(initializeFunc)(validityTestFunc)

  def create[X](z0Func: (BigComplex, BigComplex) => X)
               (zNextFunc: (BigComplex, BigComplex, Double) => X => X)
               (isValidFunc: Double => X => Boolean): BigComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.create(z0Func)(zNextFunc)(isValidFunc)

  def normed[X <: NormedNumber](z0Func: (BigComplex, BigComplex) => X)
                               (zNextFunc: (BigComplex, BigComplex) => X => X): BigComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.normed(z0Func)(zNextFunc)

  def optNormed[X <: OptNormedNumber](z0Func: (BigComplex, BigComplex) => X)
                                     (zNextFunc: (BigComplex, BigComplex) => X => X): BigComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.optNormed(z0Func)(zNextFunc)

  def any[X <: HigherVector[X, OptBigComplex, BigComplex]](z0Func: (BigComplex, BigComplex) => X)
                                                    (zNextFunc: (BigComplex, BigComplex) => X => X): BigComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.any[BigComplex, X, OptBigComplex, BigComplex](z0Func)(zNextFunc)

  def all[X <: HigherVector[X, OptBigComplex, BigComplex]](z0Func: (BigComplex, BigComplex) => X)
                                                          (zNextFunc: (BigComplex, BigComplex) => X => X): BigComplexFractalIteratorDefinitionAux[X] =
    FractalIteratorDefinition.all[BigComplex, X, OptBigComplex, BigComplex](z0Func)(zNextFunc)

}
