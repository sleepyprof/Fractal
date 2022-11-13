package de.gdietz.fun.fractal.scala.util

case class HigherVector3[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](x1: O, x2: O, x3: O)
 extends HigherVector[HigherVector3[O, X], O, X] {

  override def const(x: O): HigherVector3[O, X] =
    HigherVector3(x, x, x)

  override def map(f: O => O): HigherVector3[O, X] =
    HigherVector3(f(x1), f(x2), f(x3))

  override def mapOp(x: HigherVector3[O, X])(f: (O, O) => O): HigherVector3[O, X] =
    HigherVector3(f(x1, x.x1), f(x2, x.x2), f(x3, x.x3))

  override def forall(cond: O => Boolean): Boolean =
    cond(x1) && cond(x2) && cond(x3)

  override def exists(cond: O => Boolean): Boolean =
    cond(x1) || cond(x2) || cond(x3)

  override def filterNumber(cond: X => Boolean): HigherVector3[O, X] =
    HigherVector3(x1.filterNumber(cond), x2.filterNumber(cond), x3.filterNumber(cond))

  override def isAllNumber: Boolean =
    x1.isNumber && x2.isNumber && x3.isNumber

  override def existsNumber: Boolean =
    x1.isNumber || x2.isNumber || x3.isNumber

  override def unary_- : HigherVector3[O, X] =
    HigherVector3(-x1, -x2, -x3)

  override def +(x: HigherVector3[O, X]): HigherVector3[O, X] =
    HigherVector3(x1 + x.x1, x2 + x.x2, x3 + x.x3)

  override def -(x: HigherVector3[O, X]): HigherVector3[O, X] =
    HigherVector3(x1 - x.x1, x2 - x.x2, x3 - x.x3)

  override def *(x: HigherVector3[O, X]): HigherVector3[O, X] =
    HigherVector3(x1 * x.x1, x2 * x.x2, x3 * x.x3)

  override def inverse: HigherVector3[O, X] =
    HigherVector3(x1.inverse, x2.inverse, x3.inverse)

  override def /(x: HigherVector3[O, X]): HigherVector3[O, X] =
    HigherVector3(x1 / x.x1, x2 / x.x2, x3 / x.x3)

  override def \(x: HigherVector3[O, X]): HigherVector3[O, X] =
    HigherVector3(x1 \ x.x1, x2 \ x.x2, x3 \ x.x3)

  override def conjugate: HigherVector3[O, X] =
    HigherVector3(x1.conjugate, x2.conjugate, x3.conjugate)

  override def none: HigherVector3[O, X] =
    HigherVector3(x1.none, x2.none, x3.none)

  override def zero: HigherVector3[O, X] =
    HigherVector3(x1.zero, x2.zero, x3.zero)

  override def unit: HigherVector3[O, X] =
    HigherVector3(x1.unit, x2.unit, x3.unit)

  override def sqr: HigherVector3[O, X] =
    HigherVector3(x1.sqr, x2.sqr, x3.sqr)

  override def cube: HigherVector3[O, X] =
    HigherVector3(x1.cube, x2.cube, x3.cube)

  override def pow(n: Int): HigherVector3[O, X] =
    HigherVector3(x1.pow(n), x2.pow(n), x3.pow(n))

  override def +!(x: O): HigherVector3[O, X] =
    HigherVector3(x1 + x, x2 + x, x3 + x)

  override def +!:(x: O): HigherVector3[O, X] =
    HigherVector3(x + x1, x + x2, x + x3)

  override def -!(x: O): HigherVector3[O, X] =
    HigherVector3(x1 - x, x2 - x, x3 - x)

  override def -!:(x: O): HigherVector3[O, X] =
    HigherVector3(x - x1, x - x2, x - x3)

  override def *!(x: O): HigherVector3[O, X] =
    HigherVector3(x1 * x, x2 * x, x3 * x)

  override def *!:(x: O): HigherVector3[O, X] =
    HigherVector3(x * x1, x * x2, x * x3)

  override def /!(x: O): HigherVector3[O, X] =
    HigherVector3(x1 / x, x2 / x, x3 / x)

  override def /!:(x: O): HigherVector3[O, X] =
    HigherVector3(x / x1, x / x2, x / x3)

  override def +(r: Double): HigherVector3[O, X] =
    HigherVector3(x1 + r, x2 + r, x3 + r)

  override def -(r: Double): HigherVector3[O, X] =
    HigherVector3(x1 - r, x2 - r, x3 - r)

  override def -:(r: Double): HigherVector3[O, X] =
    HigherVector3(r -: x1, r -: x2, r -: x3)

  override def *(r: Double): HigherVector3[O, X] =
    HigherVector3(x1 * r, x2 * r, x3 * r)

  override def /(r: Double): HigherVector3[O, X] =
    HigherVector3(x1 / r, x2 / r, x3 / r)

  override def /:(r: Double): HigherVector3[O, X] =
    HigherVector3(r /: x1, r /: x2, r /: x3)


  final override def ::(x: O): HigherVector4[O, X] =
    HigherVector4(x, x1, x2, x3)

  final override def toHigherVectorN: HigherVectorN[O, X] =
    HigherVectorN(x1 :: x2 :: x3 :: Nil)


  override def toString: String =
    "(" + x1 + ", " + x2 + ", " + x3 + ")"

}


object RealVector3 {

  @inline def apply(x1: OptReal, x2: OptReal, x3: OptReal): RealVector3 =
    HigherVector3(x1, x2, x3)

  def unapply(x: RealVector3): Option[(OptReal, OptReal, OptReal)] =
    Some((x.x1, x.x2, x.x3))

}

object ComplexVector3 {

  @inline def apply(x1: OptComplex, x2: OptComplex, x3: OptComplex): ComplexVector3 =
    HigherVector3(x1, x2, x3)

  def unapply(x: ComplexVector3): Option[(OptComplex, OptComplex, OptComplex)] =
    Some((x.x1, x.x2, x.x3))

  val sigmas3: ComplexVector3 =
    ComplexVector3(Complex.one, Complex(-0.5, 0.5 * Math.sqrt(3)), Complex(-0.5, -0.5 * Math.sqrt(3)))

}

object QuaternionVector3 {

  @inline def apply(x1: OptQuaternion, x2: OptQuaternion, x3: OptQuaternion): QuaternionVector3 =
    HigherVector3(x1, x2, x3)

  def unapply(x: QuaternionVector3): Option[(OptQuaternion, OptQuaternion, OptQuaternion)] =
    Some((x.x1, x.x2, x.x3))

}
