package de.gdietz.fun.fractal.scala.util

import de.gdietz.fun.fractal.util.{Normed => JavaNormed}

trait NormedNumber extends OptNormedNumber {
  self =>

  def normSqr: Double
  @inline def norm: Double = Math.sqrt(normSqr)


  @inline override def optNormSqr: Option[Double] = Some(normSqr)
  @inline override def optNorm: Option[Double] = Some(norm)


  def toJavaNormed: JavaNormed =
    new JavaNormed with Serializable {
      override def normSqr: Double = self.normSqr
      override def norm: Double = self.norm
      override def isZero: Boolean = self.isZero
    }

}
