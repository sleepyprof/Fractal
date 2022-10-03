package de.gdietz.fun.fractal.scala.util

trait HigherRealModNumber[+X <: HigherRealModNumber[X]] {

  def +(r: Double): X
  @inline def +:(r: Double): X = this + r
  def -(r: Double): X
  def -:(r: Double): X

  def *(r: Double): X
  @inline def *:(r: Double): X = this * r

  def /(r: Double): X
  def /:(r: Double): X

}
