package de.gdietz.fun.fractal.scala.util

trait NoHigherNumber[O <: OptHigherNumber[O, X], X <: O with SomeHigherNumber[O, X]]
  extends OptHigherNumber[O, X] {
  self: O =>

  final override def isNumber: Boolean = false

  @inline final override def foldNumber[Y](ifIsNumber: X => Y)(ifNoNumber: => Y): Y = ifNoNumber

  @inline final override def mapNumber(f: X => O): O = none
  @inline final override def mapOpNumber(x: O)(f: (X, X) => O): O = none

  @inline final override def filterNumber(cond: X => Boolean): O = none

  @inline final override def isZero: Boolean = false

  @inline final override def optNormSqr: Option[Double] = None

  @inline override def unary_- : O = none

  @inline override def +(r: Double): O = none
  @inline override def +(x: O): O = none
  @inline override def -(r: Double): O = none
  @inline override def -:(r: Double): O = none
  @inline override def -(x: O): O = none

  @inline override def *(r: Double): O = none
  @inline override def *(x: O): O = none

  @inline override def inverse: O = none

  @inline override def /(r: Double): O = none
  @inline override def /:(r: Double): O = none
  @inline override def /(x: O): O = none
  @inline override def \(x: O): O = none

  @inline override def conjugate: O = none

  @inline override def sqr: O = none
  @inline override def cube: O = none

  override def pow(n: Int): O = n match {
    case 0 => unit
    case _ => none
  }

  @inline override def **(n: Int): O = pow(n)

}
