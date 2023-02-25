package de.gdietz.fun.fractal.scala.util

case class HigherVector4[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](x1: O, x2: O, x3: O, x4: O)
 extends HigherVector[HigherVector4[O, X], O, X] {

  override def const(x: O): HigherVector4[O, X] =
    HigherVector4(x, x, x, x)

  override def map(f: O => O): HigherVector4[O, X] =
    HigherVector4(f(x1), f(x2), f(x3), f(x4))

  override def mapOp(x: HigherVector4[O, X])(f: (O, O) => O): HigherVector4[O, X] =
    HigherVector4(f(x1, x.x1), f(x2, x.x2), f(x3, x.x3), f(x4, x.x4))

  override def forall(cond: O => Boolean): Boolean =
    cond(x1) && cond(x2) && cond(x3) && cond(x4)

  override def exists(cond: O => Boolean): Boolean =
    cond(x1) || cond(x2) || cond(x3) || cond(x4)

  override def filter(cond: O => Boolean): HigherVector4[O, X] =
    HigherVector4(x1.filter(cond), x2.filter(cond), x3.filter(cond), x4.filter(cond))

  override def mapNumber(f: X => O): HigherVector4[O, X] =
    HigherVector4(x1.mapNumber(f), x2.mapNumber(f), x3.mapNumber(f), x4.mapNumber(f))

  override def mapOpNumber(x: HigherVector4[O, X])(f: (X, X) => O): HigherVector4[O, X] =
    HigherVector4(x1.mapOpNumber(x.x1)(f), x2.mapOpNumber(x.x2)(f), x3.mapOpNumber(x.x3)(f), x4.mapOpNumber(x.x4)(f))

  override def filterNumber(cond: X => Boolean): HigherVector4[O, X] =
    HigherVector4(x1.filterNumber(cond), x2.filterNumber(cond), x3.filterNumber(cond), x4.filterNumber(cond))

  override def isAllNumber: Boolean =
    x1.isNumber && x2.isNumber && x3.isNumber && x4.isNumber

  override def existsNumber: Boolean =
    x1.isNumber || x2.isNumber || x3.isNumber || x4.isNumber

  override def isZero: Boolean =
    x1.isZero && x2.isZero && x3.isZero && x4.isZero

  override def optNormSqr: Option[Double] =
    x1.optNormSqr.flatMap(n1 => x2.optNormSqr.flatMap(n2 => x3.optNormSqr.flatMap(n3 => x4.optNormSqr.map(n4 => n1 + n2 + n3 + n4))))

  override def unary_- : HigherVector4[O, X] =
    HigherVector4(-x1, -x2, -x3, -x4)

  override def +(x: HigherVector4[O, X]): HigherVector4[O, X] =
    HigherVector4(x1 + x.x1, x2 + x.x2, x3 + x.x3, x4 + x.x4)

  override def -(x: HigherVector4[O, X]): HigherVector4[O, X] =
    HigherVector4(x1 - x.x1, x2 - x.x2, x3 - x.x3, x4 - x.x4)

  override def *(x: HigherVector4[O, X]): HigherVector4[O, X] =
    HigherVector4(x1 * x.x1, x2 * x.x2, x3 * x.x3, x4 * x.x4)

  override def inverse: HigherVector4[O, X] =
    HigherVector4(x1.inverse, x2.inverse, x3.inverse, x4.inverse)

  override def /(x: HigherVector4[O, X]): HigherVector4[O, X] =
    HigherVector4(x1 / x.x1, x2 / x.x2, x3 / x.x3, x4 / x.x4)

  override def \(x: HigherVector4[O, X]): HigherVector4[O, X] =
    HigherVector4(x1 \ x.x1, x2 \ x.x2, x3 \ x.x3, x4 \ x.x4)

  override def conjugate: HigherVector4[O, X] =
    HigherVector4(x1.conjugate, x2.conjugate, x3.conjugate, x4.conjugate)

  override def none: HigherVector4[O, X] =
    HigherVector4(x1.none, x2.none, x3.none, x4.none)

  override def zero: HigherVector4[O, X] =
    HigherVector4(x1.zero, x2.zero, x3.zero, x4.zero)

  override def unit: HigherVector4[O, X] =
    HigherVector4(x1.unit, x2.unit, x3.unit, x4.unit)

  override def sqr: HigherVector4[O, X] =
    HigherVector4(x1.sqr, x2.sqr, x3.sqr, x4.sqr)

  override def cube: HigherVector4[O, X] =
    HigherVector4(x1.cube, x2.cube, x3.cube, x4.cube)

  override def pow(n: Int): HigherVector4[O, X] =
    HigherVector4(x1.pow(n), x2.pow(n), x3.pow(n), x4.pow(n))

  override def +!(x: O): HigherVector4[O, X] =
    HigherVector4(x1 + x, x2 + x, x3 + x, x4 + x)

  override def +!:(x: O): HigherVector4[O, X] =
    HigherVector4(x + x1, x + x2, x + x3, x + x4)

  override def -!(x: O): HigherVector4[O, X] =
    HigherVector4(x1 - x, x2 - x, x3 - x, x4 - x)

  override def -!:(x: O): HigherVector4[O, X] =
    HigherVector4(x - x1, x - x2, x - x3, x - x4)

  override def *!(x: O): HigherVector4[O, X] =
    HigherVector4(x1 * x, x2 * x, x3 * x, x4 * x)

  override def *!:(x: O): HigherVector4[O, X] =
    HigherVector4(x * x1, x * x2, x * x3, x * x4)

  override def /!(x: O): HigherVector4[O, X] =
    HigherVector4(x1 / x, x2 / x, x3 / x, x4 / x)

  override def /!:(x: O): HigherVector4[O, X] =
    HigherVector4(x / x1, x / x2, x / x3, x / x4)

  override def +(r: Double): HigherVector4[O, X] =
    HigherVector4(x1 + r, x2 + r, x3 + r, x4 + r)

  override def -(r: Double): HigherVector4[O, X] =
    HigherVector4(x1 - r, x2 - r, x3 - r, x4 - r)

  override def -:(r: Double): HigherVector4[O, X] =
    HigherVector4(r -: x1, r -: x2, r -: x3, r -: x4)

  override def *(r: Double): HigherVector4[O, X] =
    HigherVector4(x1 * r, x2 * r, x3 * r, x4 * r)

  override def /(r: Double): HigherVector4[O, X] =
    HigherVector4(x1 / r, x2 / r, x3 / r, x4 / r)

  override def /:(r: Double): HigherVector4[O, X] =
    HigherVector4(r /: x1, r /: x2, r /: x3, r /: x4)


  final override def ::(x: O): HigherVectorN[O, X] =
    HigherVectorN(x :: x1 :: x2 :: x3 :: x4 :: Nil)

  final override def toHigherVectorN: HigherVectorN[O, X] =
    HigherVectorN(x1 :: x2 :: x3 :: x4 :: Nil)


  override def toString: String =
    "(" + x1 + ", " + x2 + ", " + x3 + ", " + x4 + ")"

}


object RealVector4 {

  @inline def apply(x1: OptReal, x2: OptReal, x3: OptReal, x4: OptReal): RealVector4 =
    HigherVector4(x1, x2, x3, x4)

  def unapply(x: RealVector4): Option[(OptReal, OptReal, OptReal, OptReal)] =
    Some((x.x1, x.x2, x.x3, x.x4))

}

object ComplexVector4 {

  @inline def apply(x1: OptComplex, x2: OptComplex, x3: OptComplex, x4: OptComplex): ComplexVector4 =
    HigherVector4(x1, x2, x3, x4)

  def unapply(x: ComplexVector4): Option[(OptComplex, OptComplex, OptComplex, OptComplex)] =
    Some((x.x1, x.x2, x.x3, x.x4))

  val sigmas4: ComplexVector4 =
    ComplexVector4(Complex.one, Complex.i, Complex.minusOne, Complex.minusI)

}

object QuaternionVector4 {

  @inline def apply(x1: OptQuaternion, x2: OptQuaternion, x3: OptQuaternion, x4: OptQuaternion): QuaternionVector4 =
    HigherVector4(x1, x2, x3, x4)

  def unapply(x: QuaternionVector4): Option[(OptQuaternion, OptQuaternion, OptQuaternion, OptQuaternion)] =
    Some((x.x1, x.x2, x.x3, x.x4))

}

object BigComplexVector4 {

  @inline def apply(x1: OptBigComplex, x2: OptBigComplex, x3: OptBigComplex, x4: OptBigComplex): BigComplexVector4 =
    HigherVector4(x1, x2, x3, x4)

  def unapply(x: BigComplexVector4): Option[(OptBigComplex, OptBigComplex, OptBigComplex, OptBigComplex)] =
    Some((x.x1, x.x2, x.x3, x.x4))

}

