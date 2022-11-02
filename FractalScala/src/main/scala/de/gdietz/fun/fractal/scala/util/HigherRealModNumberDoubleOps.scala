package de.gdietz.fun.fractal.scala.util

import scala.language.implicitConversions

class HigherRealModNumberDoubleOps(private val r: Double) extends AnyVal {

  @inline final def +[X <: HigherRealModNumber[X]](x: X): X = r +: x
  @inline final def -[X <: HigherRealModNumber[X]](x: X): X = r -: x
  @inline final def *[X <: HigherRealModNumber[X]](x: X): X = r *: x
  @inline final def /[X <: HigherRealModNumber[X]](x: X): X = r /: x

}

object HigherRealModNumberDoubleOps {

  trait ToHigherRealModNumberDoubleOps {
    implicit def toHigherRealModNumberDoubleOps(r: Double): HigherRealModNumberDoubleOps =
      new HigherRealModNumberDoubleOps(r)
  }

  object ops extends ToHigherRealModNumberDoubleOps

}
