package de.gdietz.fun.fractal.scala.util

trait HigherNumber[X <: HigherNumber[X]]
  extends HigherRealModNumber[X] with NormedNumber {
  self: X =>

  def isUnit: Boolean

  def unary_+ : X = this
  def unary_- : X

  def +(x: X): X
  def -(x: X): X

  def *(x: X): X

  def inverse: X

  def /(x: X): X = this * x.inverse
  def \(x: X): X = x.inverse * this

  def conjugate: X

  def zero: X
  def unit: X

  def sqr: X
  def cube: X

  def pow(n: Int): X = n match {
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

  @inline def **(n: Int): X = pow(n)

}
