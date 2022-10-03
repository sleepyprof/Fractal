package de.gdietz.fun.fractal.scala.util

trait NormedNumber {

  def normSqr: Double
  def norm: Double

  def isZero: Boolean

}
