package de.gdietz.fun.fractal.scala.util

trait HigherHoloNumber[X <: HigherHoloNumber[X]]
  extends HigherNumber[X] {
  self: X =>

  def exp: X
  def log: X
  def log(branch: Int): X

  def pow(x: X): X =
    if (isZero) this
    else (log * x).exp

  def pow(x: X, branch: Int): X =
    if (isZero) this
    else (log(branch) * x).exp

  def pow(d: Double): X =
    if (isZero) this
    else (log * d).exp

  def pow(d: Double, branch: Int): X =
    if (isZero) this
    else (log(branch) * d).exp

  @inline def **(x: X): X = pow(x)
  @inline def **(d: Double): X = pow(d)


  def sqrt: X


  def sin: X
  def cos: X
  def sinh: X
  def cosh: X

  def tan: X = sin / cos
  def tanh: X = sinh / cosh

}
