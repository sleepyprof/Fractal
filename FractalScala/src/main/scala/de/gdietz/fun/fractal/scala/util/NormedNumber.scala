package de.gdietz.fun.fractal.scala.util

trait NormedNumber extends Serializable {

  def normSqr: Double
  @inline def norm: Double = Math.sqrt(normSqr)

  def isZero: Boolean

}
