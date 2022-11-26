package de.gdietz.fun.fractal.scala.util

trait HigherRealModNumber[+X <: HigherRealModNumber[X]]
  extends RealModVector[X] {
  self: X =>

  def +(r: Double): X
  @inline final def +:(r: Double): X = this + r
  def -(r: Double): X
  def -:(r: Double): X

  def *(r: Double): X

  def /(r: Double): X
  def /:(r: Double): X

}
