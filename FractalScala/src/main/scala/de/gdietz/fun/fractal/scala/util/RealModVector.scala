package de.gdietz.fun.fractal.scala.util

trait RealModVector[+X <: RealModVector[X]] {
  self: X =>

  def *(r: Double): X
  @inline final def *:(r: Double): X = this * r

  def /(r: Double): X

}
