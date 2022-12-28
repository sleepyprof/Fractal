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


final class HigherHoloVectorOps[V <: HigherVector[V, O, X], O <: OptHigherNumber[O, X], X <: O with HigherHoloNumber[X]](private val v: HigherVector[V, O, X]) extends AnyVal {

  def exp: V = v.mapNumber(_.exp)
  def log: V = v.mapNumber(_.log)

  def pow(w: V): V = v.mapOpNumber(w)(_.pow(_))
  @inline def **(x: V): V = pow(x)
  def pow(x: X): V = v.mapNumber(_.pow(x))
  @inline def **(x: X): V = pow(x)
  def pow(d: Double): V = v.mapNumber(_.pow(d))
  @inline def **(d: Double): V = pow(d)

  def sqrt: V = v.mapNumber(_.sqrt)

  def sin: V = v.mapNumber(_.sin)
  def cos: V = v.mapNumber(_.cos)
  def sinh: V = v.mapNumber(_.sinh)
  def cosh: V = v.mapNumber(_.cosh)
  def tan: V = v.mapNumber(_.tan)
  def tanh: V = v.mapNumber(_.tanh)


}

object HigherHoloVectorOps {

  trait ToHigherHoloVectorOps {
    implicit def toHigherHoloVectorOps[V <: HigherVector[V, O, X], O <: OptHigherNumber[O, X], X <: O with HigherHoloNumber[X]](v: HigherVector[V, O, X]): HigherHoloVectorOps[V, O, X] =
      new HigherHoloVectorOps(v)
  }

  object ops extends ToHigherHoloVectorOps

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


final class OptHigherHoloNumberOps[O <: OptHigherNumber[O, X], X <: O with HigherHoloNumber[X]](private val x: OptHigherNumber[O, X]) extends AnyVal {

  def roots2: HigherVector2[O, X] = {
    val sqrt0 = x.mapNumber(_.sqrt)
    HigherVector2(sqrt0, -sqrt0)
  }

  def roots(n: Int): HigherVectorN[O, X] = {
    val b = List.newBuilder[O]
    if (n != 0) {
      val r = 1.0 / n
      var k = 0
      while (k < n) {
        b += x.mapNumber(_.pow(r, k))
        k += 1
      }
    }
    HigherVectorN(b.result())
  }

}

object OptHigherHoloNumberOps {

  trait ToOptHigherHoloNumberOps {
    implicit def toOptHigherHoloNumberOps[O <: OptHigherNumber[O, X], X <: O with HigherHoloNumber[X]](x: OptHigherNumber[O, X]): OptHigherHoloNumberOps[O, X] =
      new OptHigherHoloNumberOps(x)
  }

  object ops extends ToOptHigherHoloNumberOps

}


object HigherNumberOps
