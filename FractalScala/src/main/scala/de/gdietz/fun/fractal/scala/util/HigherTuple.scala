package de.gdietz.fun.fractal.scala.util

trait HigherTuple[V <: HigherTuple[V]]
  extends HigherRealModNumber[V] {
  self: V =>

  def isAllNumber: Boolean
  def existsNumber: Boolean


  @inline def unary_+ : V = this
  def unary_- : V

  def +(x: V): V
  def -(x: V): V

  def *(x: V): V

  def inverse: V

  def /(x: V): V = this * x.inverse
  def \(x: V): V = x.inverse * this

  def conjugate: V

  def none: V
  def zero: V
  def unit: V

  def sqr: V
  def cube: V

  def pow(n: Int): V = n match {
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

  @inline def **(n: Int): V = pow(n)

}
