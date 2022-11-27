package de.gdietz.fun.fractal.scala.util

trait SomeHigherNumber[O <: OptHigherNumber[O, X], X <: O with SomeHigherNumber[O, X]]
  extends HigherNumber[X] with OptHigherNumber[O, X] {
  self: X =>

  final override def isNumber: Boolean = true

  @inline final override def foldNumber[Y](ifIsNumber: X => Y)(ifNoNumber: => Y): Y = ifIsNumber(this)

  @inline final override def mapNumber(f: X => O): O = f(this)
  @inline final override def mapOpNumber(x: O)(f: (X, X) => O): O = x.mapNumber(f(this, _))

  final override def filterNumber(cond: X => Boolean): O = if (cond(this)) this else none

  @inline final override def unary_+ : X = this
  override def unary_- : X

  override def +(x: O): O = x.foldNumber[O](x1 => (this: X) + x1: X)(none)
  override def -:(r: Double): X = (-this) + r
  override def -(x: O): O = x.foldNumber[O](x1 => (this: X) - x1: X)(none)

  override def *(x: O): O = x.foldNumber[O](x1 => (this: X) * x1: X)(none)

  override def inverse: X

  override def /:(r: Double): X = inverse * r
  override def /(x: O): O = x.foldNumber[O](x1 => (this: X) / x1: X)(none)
  override def \(x: O): O = x.foldNumber[O](x1 => (this: X) \ x1: X)(none)

  override def conjugate: X

  override def sqr: X
  override def cube: X

  override def pow(n: Int): X = n match {
    case 2 => sqr
    case 3 => cube
    case -1 => inverse
    case 1 => this
    case 0 => unit
    case n if n < 0 => inverse.pow(-n)
    case n if n % 3 == 0 => cube.pow(n / 3)
    case n if n % 2 == 0 => sqr.pow(n / 2)
    case _ => this * sqr.pow((n - 1) / 2)
  }

  @inline override def **(n: Int): X = pow(n)

}
