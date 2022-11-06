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

  override def filterNumber(cond: X => Boolean): HigherVector2[O, X] =
    HigherVector2(x1.filterNumber(cond), x2.filterNumber(cond))

  override def isAllNumber: Boolean =
    x1.isNumber && x2.isNumber

  override def existsNumber: Boolean =
    x1.isNumber || x2.isNumber

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


  override def toString: String =
    "(" + x1 + ", " + x2 + ")"

}


object RealVector2 {

  def apply(x1: OptReal, x2: OptReal): RealVector2 =
    HigherVector2(x1, x2)

  def unapply(x: RealVector2): Option[(OptReal, OptReal)] =
    Some((x.x1, x.x2))

}

object ComplexVector2 {

  def apply(x1: OptComplex, x2: OptComplex): ComplexVector2 =
    HigherVector2(x1, x2)

  def unapply(x: ComplexVector2): Option[(OptComplex, OptComplex)] =
    Some((x.x1, x.x2))

}

object QuaternionVector2 {

  def apply(x1: OptQuaternion, x2: OptQuaternion): QuaternionVector2 =
    HigherVector2(x1, x2)

  def unapply(x: QuaternionVector2): Option[(OptQuaternion, OptQuaternion)] =
    Some((x.x1, x.x2))

}
