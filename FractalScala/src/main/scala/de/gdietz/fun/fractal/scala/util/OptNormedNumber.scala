package de.gdietz.fun.fractal.scala.util

trait OptNormedNumber extends Serializable {

  def isZero: Boolean

  def optNormSqr: Option[Double]
  @inline def optNorm: Option[Double] = optNormSqr.map(Math.sqrt)

}
