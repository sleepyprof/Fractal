package de.gdietz.fun.fractal.scala.util

trait Vector[X <: Vector[X]]
  extends RealModVector[X] with NormedNumber {
  self: X =>

  def unary_+ : X = this
  def unary_- : X

  def +(x: X): X
  def -(x: X): X

  def zero: X

}
