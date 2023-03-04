package de.gdietz.fun.fractal.scala.util

case class HigherVector2[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](x1: O, x2: O)
 extends HigherVector[HigherVector2[O, X], O, X] {

  override def const(x: O): HigherVector2[O, X] =
    HigherVector2(x, x)

  override def map(f: O => O): HigherVector2[O, X] =
    HigherVector2(f(x1), f(x2))

  override def mapOp(x: HigherVector2[O, X])(f: (O, O) => O): HigherVector2[O, X] =
    HigherVector2(f(x1, x.x1), f(x2, x.x2))

  override def forall(cond: O => Boolean): Boolean =
    cond(x1) && cond(x2)

  override def exists(cond: O => Boolean): Boolean =
    cond(x1) || cond(x2)

  override def filter(cond: O => Boolean): HigherVector2[O, X] =
    HigherVector2(x1.filter(cond), x2.filter(cond))

  override def mapNumber(f: X => O): HigherVector2[O, X] =
    HigherVector2(x1.mapNumber(f), x2.mapNumber(f))

  override def mapOpNumber(x: HigherVector2[O, X])(f: (X, X) => O): HigherVector2[O, X] =
    HigherVector2(x1.mapOpNumber(x.x1)(f), x2.mapOpNumber(x.x2)(f))

  override def filterNumber(cond: X => Boolean): HigherVector2[O, X] =
    HigherVector2(x1.filterNumber(cond), x2.filterNumber(cond))

  override def isAllNumber: Boolean =
    x1.isNumber && x2.isNumber

  override def existsNumber: Boolean =
    x1.isNumber || x2.isNumber

  override def isZero: Boolean =
    x1.isZero && x2.isZero

  override def optNormSqr: Option[Double] =
    x1.optNormSqr.flatMap(n1 => x2.optNormSqr.map(n2 => n1 + n2))

  override def unary_- : HigherVector2[O, X] =
    HigherVector2(-x1, -x2)

  override def +(x: HigherVector2[O, X]): HigherVector2[O, X] =
    HigherVector2(x1 + x.x1, x2 + x.x2)

  override def -(x: HigherVector2[O, X]): HigherVector2[O, X] =
    HigherVector2(x1 - x.x1, x2 - x.x2)

  override def *(x: HigherVector2[O, X]): HigherVector2[O, X] =
    HigherVector2(x1 * x.x1, x2 * x.x2)

  override def inverse: HigherVector2[O, X] =
    HigherVector2(x1.inverse, x2.inverse)

  override def /(x: HigherVector2[O, X]): HigherVector2[O, X] =
    HigherVector2(x1 / x.x1, x2 / x.x2)

  override def \(x: HigherVector2[O, X]): HigherVector2[O, X] =
    HigherVector2(x1 \ x.x1, x2 \ x.x2)

  override def conjugate: HigherVector2[O, X] =
    HigherVector2(x1.conjugate, x2.conjugate)

  override def none: HigherVector2[O, X] =
    HigherVector2(x1.none, x2.none)

  override def zero: HigherVector2[O, X] =
    HigherVector2(x1.zero, x2.zero)

  override def unit: HigherVector2[O, X] =
    HigherVector2(x1.unit, x2.unit)

  override def sqr: HigherVector2[O, X] =
    HigherVector2(x1.sqr, x2.sqr)

  override def cube: HigherVector2[O, X] =
    HigherVector2(x1.cube, x2.cube)

  override def pow(n: Int): HigherVector2[O, X] =
    HigherVector2(x1.pow(n), x2.pow(n))

  override def +!(x: O): HigherVector2[O, X] =
    HigherVector2(x1 + x, x2 + x)

  override def +!:(x: O): HigherVector2[O, X] =
    HigherVector2(x + x1, x + x2)

  override def -!(x: O): HigherVector2[O, X] =
    HigherVector2(x1 - x, x2 - x)

  override def -!:(x: O): HigherVector2[O, X] =
    HigherVector2(x - x1, x - x2)

  override def *!(x: O): HigherVector2[O, X] =
    HigherVector2(x1 * x, x2 * x)

  override def *!:(x: O): HigherVector2[O, X] =
    HigherVector2(x * x1, x * x2)

  override def /!(x: O): HigherVector2[O, X] =
    HigherVector2(x1 / x, x2 / x)

  override def /!:(x: O): HigherVector2[O, X] =
    HigherVector2(x / x1, x / x2)

  override def +(r: Double): HigherVector2[O, X] =
    HigherVector2(x1 + r, x2 + r)

  override def -(r: Double): HigherVector2[O, X] =
    HigherVector2(x1 - r, x2 - r)

  override def -:(r: Double): HigherVector2[O, X] =
    HigherVector2(r -: x1, r -: x2)

  override def *(r: Double): HigherVector2[O, X] =
    HigherVector2(x1 * r, x2 * r)

  override def /(r: Double): HigherVector2[O, X] =
    HigherVector2(x1 / r, x2 / r)

  override def /:(r: Double): HigherVector2[O, X] =
    HigherVector2(r /: x1, r /: x2)


  final override def ::(x: O): HigherVector3[O, X] =
    HigherVector3(x, x1, x2)

  final override def toHigherVectorN: HigherVectorN[O, X] =
    HigherVectorN(x1 :: x2 :: Nil)


  override def toString: String =
    "(" + x1 + ", " + x2 + ")"

}


object RealVector2 {

  @inline def apply(x1: OptReal, x2: OptReal): RealVector2 =
    HigherVector2(x1, x2)

  def unapply(x: RealVector2): Option[(OptReal, OptReal)] =
    Some((x.x1, x.x2))

  val zero: RealVector2 =
    RealVector2(Real.zero, Real.zero)

}

object ComplexVector2 {

  @inline def apply(x1: OptComplex, x2: OptComplex): ComplexVector2 =
    HigherVector2(x1, x2)

  def unapply(x: ComplexVector2): Option[(OptComplex, OptComplex)] =
    Some((x.x1, x.x2))

  val sigmas2: ComplexVector2 =
    ComplexVector2(Complex.one, Complex.minusOne)

}

object QuaternionVector2 {

  @inline def apply(x1: OptQuaternion, x2: OptQuaternion): QuaternionVector2 =
    HigherVector2(x1, x2)

  def unapply(x: QuaternionVector2): Option[(OptQuaternion, OptQuaternion)] =
    Some((x.x1, x.x2))

}

object BigRealVector2 {

  @inline def apply(x1: OptBigReal, x2: OptBigReal): BigRealVector2 =
    HigherVector2(x1, x2)

  def unapply(x: BigRealVector2): Option[(OptBigReal, OptBigReal)] =
    Some((x.x1, x.x2))

}

object BigComplexVector2 {

  @inline def apply(x1: OptBigComplex, x2: OptBigComplex): BigComplexVector2 =
    HigherVector2(x1, x2)

  def unapply(x: BigComplexVector2): Option[(OptBigComplex, OptBigComplex)] =
    Some((x.x1, x.x2))

}
