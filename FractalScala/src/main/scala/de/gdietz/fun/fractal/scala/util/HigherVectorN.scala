package de.gdietz.fun.fractal.scala.util

import scala.annotation.tailrec

case class HigherVectorN[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](xs: List[O])
 extends HigherVector[HigherVectorN[O, X], O, X] {

  override def const(x: O): HigherVectorN[O, X] =
    HigherVectorN(x)

  override def map(f: O => O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(f))

  override def mapOp(x: HigherVectorN[O, X])(f: (O, O) => O): HigherVectorN[O, X] =
    HigherVectorN(HigherVectorN.mapOp(xs, x.xs)(f))

  override def forall(cond: O => Boolean): Boolean =
    xs.forall(cond)

  override def exists(cond: O => Boolean): Boolean =
    xs.exists(cond)

  override def filter(cond: O => Boolean): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.filter(cond)))

  override def mapNumber(f: X => O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.mapNumber(f)))

  override def mapOpNumber(x: HigherVectorN[O, X])(f: (X, X) => O): HigherVectorN[O, X] =
    HigherVectorN(HigherVectorN.mapOp(xs, x.xs)(_.mapOpNumber(_)(f)))

  override def filterNumber(cond: X => Boolean): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.filterNumber(cond)))

  override def isAllNumber: Boolean =
    xs.forall(_.isNumber)

  override def existsNumber: Boolean =
    xs.exists(_.isNumber)

  override def isZero: Boolean =
    xs.forall(_.isZero)

  override def optNormSqr: Option[Double] =
    HigherVectorN.foldNormSqr(xs, 0.0)

  override def unary_- : HigherVectorN[O, X] =
    HigherVectorN(xs.map(-_))

  override def +(x: HigherVectorN[O, X]): HigherVectorN[O, X] =
    HigherVectorN(HigherVectorN.mapOp(xs, x.xs)(_ + _))

  override def -(x: HigherVectorN[O, X]): HigherVectorN[O, X] =
    HigherVectorN(HigherVectorN.mapOp(xs, x.xs)(_ - _))

  override def *(x: HigherVectorN[O, X]): HigherVectorN[O, X] =
    HigherVectorN(HigherVectorN.mapOp(xs, x.xs)(_ * _))

  override def inverse: HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.inverse))

  override def /(x: HigherVectorN[O, X]): HigherVectorN[O, X] =
    HigherVectorN(HigherVectorN.mapOp(xs, x.xs)(_ / _))

  override def \(x: HigherVectorN[O, X]): HigherVectorN[O, X] =
    HigherVectorN(HigherVectorN.mapOp(xs, x.xs)(_ \ _))

  override def conjugate: HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.conjugate))

  override def none: HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.none))

  override def zero: HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.zero))

  override def unit: HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.unit))

  override def sqr: HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.sqr))

  override def cube: HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.cube))

  override def pow(n: Int): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_.pow(n)))

  override def +!(x: O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_ + x))

  override def +!:(x: O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(x + _))

  override def -!(x: O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_ - x))

  override def -!:(x: O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(x - _))

  override def *!(x: O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_ * x))

  override def *!:(x: O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(x * _))

  override def /!(x: O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_ / x))

  override def /!:(x: O): HigherVectorN[O, X] =
    HigherVectorN(xs.map(x / _))

  override def +(r: Double): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_ + r))

  override def -(r: Double): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_ - r))

  override def -:(r: Double): HigherVectorN[O, X] =
    HigherVectorN(xs.map(r -: _))

  override def *(r: Double): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_ * r))

  override def /(r: Double): HigherVectorN[O, X] =
    HigherVectorN(xs.map(_ / r))

  override def /:(r: Double): HigherVectorN[O, X] =
    HigherVectorN(xs.map(r /: _))


  final override def ::(x: O): HigherVectorN[O, X] =
    HigherVectorN(x :: xs)

  final override def toHigherVectorN: HigherVectorN[O, X] = this


  override def toString: String =
    xs.mkString("(", ", ", ")")

}

object HigherVectorN {

  def apply[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]](xs: O*): HigherVectorN[O, X] =
    HigherVectorN(xs.toList)

  @tailrec private def foldNormSqr[X <: OptNormedNumber](xs: List[X], acc: Double): Option[Double] =
    xs match {
      case h :: t =>
        h.optNormSqr match {
          case Some(hn) => foldNormSqr(t, acc + hn)
          case _ => None
        }
      case _ => Some(acc)
    }

  private def mapOp[X, Y, Z](xs: List[X], ys: List[Y])(f: (X, Y) => Z): List[Z] =
    xs match {
      case xh1 :: xt1 =>
        ys match {
          case yh1 :: yt1 =>
            val b = List.newBuilder[Z]

            @tailrec def loop(xh: X, xt: List[X], yh: Y, yt: List[Y], x0: Boolean, y0: Boolean): Unit = {
              b += f(xh, yh)
              xt match {
                case xh2 :: xt2 =>
                  yt match {
                    case yh2 :: yt2 =>
                      loop(xh2, xt2, yh2, yt2, x0, y0)
                    case _ =>
                      if (x0)
                        loop(xh2, xt2, yh1, yt1, x0 = true, y0 = false)
                  }
                case _ =>
                  yt match {
                    case yh2 :: yt2 =>
                      if (y0)
                        loop(xh1, xt1, yh2, yt2, x0 = false, y0 = true)
                    case _ =>
                  }
              }
            }

            loop(xh1, xt1, yh1, yt1, x0 = true, y0 = true)
            b.result()
          case _ => Nil
        }
      case _ => Nil
    }

}


object RealVectorN {

  @inline def apply(xs: List[OptReal]): RealVectorN =
    HigherVectorN(xs)

  @inline def apply(xs: OptReal*): RealVectorN =
    HigherVectorN(xs: _*)

  def unapply(x: RealVectorN): Option[List[OptReal]] =
    Some(x.xs)

}

object ComplexVectorN {

  @inline def apply(xs: List[OptComplex]): ComplexVectorN =
    HigherVectorN(xs)

  @inline def apply(xs: OptComplex*): ComplexVectorN =
    HigherVectorN(xs: _*)

  def unapply(x: ComplexVectorN): Option[List[OptComplex]] =
    Some(x.xs)


  def sigmas(n: Int): ComplexVectorN = {
    val phi1 = Complex.pi2 / n
    ComplexVectorN((0 until n).view.map { k =>
      val phi = phi1 * k
      Complex(Math.cos(phi), Math.sin(phi))
    }.toList)
  }

}

object QuaternionVectorN {

  @inline def apply(xs: List[OptQuaternion]): QuaternionVectorN =
    HigherVectorN(xs)

  @inline def apply(xs: OptQuaternion*): QuaternionVectorN =
    HigherVectorN(xs: _*)

  def unapply(x: QuaternionVectorN): Option[List[OptQuaternion]] =
    Some(x.xs)

}

object BigRealVectorN {

  @inline def apply(xs: List[OptBigReal]): BigRealVectorN =
    HigherVectorN(xs)

  @inline def apply(xs: OptBigReal*): BigRealVectorN =
    HigherVectorN(xs: _*)

  def unapply(x: BigRealVectorN): Option[List[OptBigReal]] =
    Some(x.xs)

}

object BigComplexVectorN {

  @inline def apply(xs: List[OptBigComplex]): BigComplexVectorN =
    HigherVectorN(xs)

  @inline def apply(xs: OptBigComplex*): BigComplexVectorN =
    HigherVectorN(xs: _*)

  def unapply(x: BigComplexVectorN): Option[List[OptBigComplex]] =
    Some(x.xs)

}
