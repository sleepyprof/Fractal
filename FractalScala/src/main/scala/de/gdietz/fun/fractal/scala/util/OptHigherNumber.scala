package de.gdietz.fun.fractal.scala.util

trait OptHigherNumber[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]]
  extends HigherRealModNumber[O] {
  self: O =>

  def isNumber: Boolean

  def foldNumber[Y](ifIsNumber: X => Y)(ifNoNumber: => Y): Y
  def filterNumber(cond: X => Boolean): O

  def unary_+ : O = this
  def unary_- : O

  def +(x: O): O
  def -(x: O): O

  def *(x: O): O

  def inverse: O

  def /(x: O): O = this * x.inverse
  def \(x: O): O = x.inverse * this

  def conjugate: O

  def none: O
  def zero: X
  def unit: X

  def sqr: O
  def cube: O

  def pow(n: Int): O = n match {
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

  @inline def **(n: Int): O = pow(n)

}
