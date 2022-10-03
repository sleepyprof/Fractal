package de.gdietz.fun.fractal.scala.util

import scala.language.implicitConversions

class HigherRealModNumberDoubleOps(val r: Double) extends AnyVal {

  def +[X <: HigherRealModNumber[X]](x: X): X = r +: x
  def -[X <: HigherRealModNumber[X]](x: X): X = r -: x
  def *[X <: HigherRealModNumber[X]](x: X): X = r *: x
  def /[X <: HigherRealModNumber[X]](x: X): X = r /: x

}

trait HigherRealModNumberDoubleOpsInstances {

  implicit def toHigherRealModNumberDoubleOps(r: Double): HigherRealModNumberDoubleOps =
    new HigherRealModNumberDoubleOps(r)

}

object HigherRealModNumberDoubleOpsInstances extends HigherRealModNumberDoubleOpsInstances
