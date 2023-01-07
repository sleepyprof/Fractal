package de.gdietz.fun.fractal.scala.util

trait HigherVector[V <: HigherVector[V, O, X], O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]]
  extends HigherTuple[V] {
  self: V =>

  def const(x: O): V
  def map(f: O => O): V
  def mapOp(x: V)(f: (O, O) => O): V

  def forall(cond: O => Boolean): Boolean
  def exists(cond: O => Boolean): Boolean

  def filter(cond: O => Boolean): V

  def mapNumber(f: X => O): V
  def mapOpNumber(x: V)(f: (X, X) => O): V

  def filterNumber(cond: X => Boolean): V

  override def isAllNumber: Boolean = forall(_.isNumber)
  override def existsNumber: Boolean = exists(_.isNumber)


  def +!(x: O): V
  def +!:(x: O): V
  def -!(x: O): V
  def -!:(x: O): V

  def *!(x: O): V
  def *!:(x: O): V

  def /!(x: O): V
  def /!:(x: O): V


  def ::(x: O): HigherVector[_, O, X]
  def toHigherVectorN: HigherVectorN[O, X]

}


object RealVector {

  @inline def apply(x1: OptReal): RealVector[OptReal] =
    x1

  @inline def apply(x1: OptReal, x2: OptReal): RealVector[RealVector2] =
    RealVector2(x1, x2)

  @inline def apply(x1: OptReal, x2: OptReal, x3: OptReal): RealVector[RealVector3] =
    RealVector3(x1, x2, x3)

  @inline def apply(x1: OptReal, x2: OptReal, x3: OptReal, x4: OptReal): RealVector[RealVector4] =
    RealVector4(x1, x2, x3, x4)

  @inline def apply(xs: OptReal*): RealVector[RealVectorN] =
    RealVectorN(xs: _*)

}

object ComplexVector {

  @inline def apply(x1: OptComplex): ComplexVector[OptComplex] =
    x1

  @inline def apply(x1: OptComplex, x2: OptComplex): ComplexVector[ComplexVector2] =
    ComplexVector2(x1, x2)

  @inline def apply(x1: OptComplex, x2: OptComplex, x3: OptComplex): ComplexVector[ComplexVector3] =
    ComplexVector3(x1, x2, x3)

  @inline def apply(x1: OptComplex, x2: OptComplex, x3: OptComplex, x4: OptComplex): ComplexVector[ComplexVector4] =
    ComplexVector4(x1, x2, x3, x4)

  @inline def apply(xs: OptComplex*): ComplexVector[ComplexVectorN] =
    ComplexVectorN(xs: _*)


  @inline def sigmas2: ComplexVector[ComplexVector2] =
    ComplexVector2.sigmas2

  @inline def sigmas3: ComplexVector[ComplexVector3] =
    ComplexVector3.sigmas3

  @inline def sigmas4: ComplexVector[ComplexVector4] =
    ComplexVector4.sigmas4

  @inline def sigmas(n: Int): ComplexVector[ComplexVectorN] =
    ComplexVectorN.sigmas(n)

}

object QuaternionVector {

  @inline def apply(x1: OptQuaternion): QuaternionVector[OptQuaternion] =
    x1

  @inline def apply(x1: OptQuaternion, x2: OptQuaternion): QuaternionVector[QuaternionVector2] =
    QuaternionVector2(x1, x2)

  @inline def apply(x1: OptQuaternion, x2: OptQuaternion, x3: OptQuaternion): QuaternionVector[QuaternionVector3] =
    QuaternionVector3(x1, x2, x3)

  @inline def apply(x1: OptQuaternion, x2: OptQuaternion, x3: OptQuaternion, x4: OptQuaternion): QuaternionVector[QuaternionVector4] =
    QuaternionVector4(x1, x2, x3, x4)

  @inline def apply(xs: OptQuaternion*): QuaternionVector[QuaternionVectorN] =
    QuaternionVectorN(xs: _*)

}

object BigComplexVector {

  @inline def apply(x1: OptBigComplex): BigComplexVector[OptBigComplex] =
    x1

  @inline def apply(x1: OptBigComplex, x2: OptBigComplex): BigComplexVector[BigComplexVector2] =
    BigComplexVector2(x1, x2)

  @inline def apply(x1: OptBigComplex, x2: OptBigComplex, x3: OptBigComplex): BigComplexVector[BigComplexVector3] =
    BigComplexVector3(x1, x2, x3)

  @inline def apply(x1: OptBigComplex, x2: OptBigComplex, x3: OptBigComplex, x4: OptBigComplex): BigComplexVector[BigComplexVector4] =
    BigComplexVector4(x1, x2, x3, x4)

  @inline def apply(xs: OptBigComplex*): BigComplexVector[BigComplexVectorN] =
    BigComplexVectorN(xs: _*)

}
