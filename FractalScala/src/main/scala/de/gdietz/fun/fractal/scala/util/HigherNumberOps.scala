package de.gdietz.fun.fractal.scala.util

import scala.language.implicitConversions

final class HigherRealModNumberDoubleOps(private val r: Double) extends AnyVal {

  @inline def +[X <: HigherRealModNumber[X]](x: X): X = r +: x
  @inline def -[X <: HigherRealModNumber[X]](x: X): X = r -: x
  @inline def *[X <: HigherRealModNumber[X]](x: X): X = r *: x
  @inline def /[X <: HigherRealModNumber[X]](x: X): X = r /: x

}

object HigherRealModNumberDoubleOps {

  trait ToHigherRealModNumberDoubleOps {
    implicit def toHigherRealModNumberDoubleOps(r: Double): HigherRealModNumberDoubleOps =
      new HigherRealModNumberDoubleOps(r)
  }

  object ops extends ToHigherRealModNumberDoubleOps

}


final class HigherVectorOps[V <: HigherVector[V, O, X], O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](private val v: HigherVector[V, O, X]) extends AnyVal {
  
  @inline def +(x: O): V = v +! x
  @inline def +:(x: O): V = x +!: v
  @inline def -(x: O): V = v -! x
  @inline def -:(x: O): V = x -!: v
  @inline def *(x: O): V = v *! x
  @inline def *:(x: O): V = x *!: v
  @inline def /(x: O): V = v /! x
  @inline def /:(x: O): V = x /!: v

}

object HigherVectorOps {

  trait ToHigherVectorOps {
    implicit def toHigherVectorOps[V <: HigherVector[V, O, X], O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](v: HigherVector[V, O, X]): HigherVectorOps[V, O, X] =
      new HigherVectorOps(v)
  }

  object ops extends ToHigherVectorOps

}


final class OptHigherNumberOps[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](private val x: OptHigherNumber[O, X]) extends AnyVal {

  @inline def +[V <: HigherVector[V, O, X]](v: V): V = x +! v
  @inline def -[V <: HigherVector[V, O, X]](v: V): V = x -! v
  @inline def *[V <: HigherVector[V, O, X]](v: V): V = x *! v
  @inline def /[V <: HigherVector[V, O, X]](v: V): V = x /! v

}

object OptHigherNumberOps {

  trait ToOptHigherNumberOps {
    implicit def toOptHigherNumberOps[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](x: OptHigherNumber[O, X]): OptHigherNumberOps[O, X] =
      new OptHigherNumberOps(x)
  }

  object ops extends ToOptHigherNumberOps

}


object HigherNumberOps
