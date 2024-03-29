package de.gdietz.fun.fractal.scala.util

import de.gdietz.fun.fractal.util.{ComplexNumberFormatter, Coordinate4D, Quaternion => JavaQuaternion}
import de.gdietz.util.NumberFormatHelper

import java.text.NumberFormat
import scala.language.implicitConversions

sealed trait OptQuaternion
  extends OptHigherNumber[OptQuaternion, Quaternion]
    with Product with Serializable {

  override def none: OptQuaternion = NoQuaternion
  override def zero: Quaternion = Quaternion.zero
  override def unit: Quaternion = Quaternion.one

  def toRealVector: RealVector4

}

object OptQuaternion {

  def apply(x: Double, y: Double = 0.0, z: Double = 0.0, w: Double = 0.0): OptQuaternion =
    Quaternion(x, y, z, w)

  val none: OptQuaternion = NoQuaternion


  implicit def doubleToOptQuaternion(x: Double): OptQuaternion =
    Quaternion(x)

  implicit def optRealToOptQuaternion(x: OptReal): OptQuaternion =
    x match {
      case Real(x) => Quaternion(x)
      case _ => NoQuaternion
    }

  implicit def optComplexToOptQuaternion(c: OptComplex): OptQuaternion =
    c match {
      case Complex(x, y) => Quaternion(x, y)
      case _ => NoQuaternion
    }

  implicit def coordinateToOptQuaternion(c: Coordinate4D): OptQuaternion =
    Quaternion(c.getX, c.getY, c.getZ, c.getW)

}


case class Quaternion(x: Double, y: Double = 0.0, z: Double = 0.0, w: Double = 0.0)
  extends OptQuaternion
    with SomeHigherNumber[OptQuaternion, Quaternion]
    with HigherHoloNumber[Quaternion] {

  override def isZero: Boolean = x == 0.0 && y == 0.0 && z == 0.0 && w == 0.0

  override def isUnit: Boolean = x == 1.0 && y == 0.0 && z == 0.0 && w == 0.0

  override def normSqr: Double = x * x + y * y + z * z + w * w

  def imagNormSqr: Double = y * y + z * z + w * w

  def imagNorm: Double = Math.sqrt(imagNormSqr)

  override def unary_- : Quaternion = Quaternion(-x, -y, -z, -w)

  override def +(r: Double): Quaternion = Quaternion(x + r, y, z, w)

  override def +(q: Quaternion): Quaternion = Quaternion(x + q.x, y + q.y, z + q.z, w + q.w)

  override def -(r: Double): Quaternion = Quaternion(x - r, y, z, w)

  override def -:(r: Double): Quaternion = Quaternion(r - x, -y, -z, -w)

  override def -(q: Quaternion): Quaternion = Quaternion(x - q.x, y - q.y, z - q.z, w - q.w)

  override def *(r: Double): Quaternion = Quaternion(r * x, r * y, r * z, r * w)

  override def *(q: Quaternion): Quaternion =
    Quaternion(x * q.x - y * q.y - z * q.z - w * q.w,
      x * q.y + y * q.x + z * q.w - w * q.z,
      x * q.z + z * q.x + w * q.y - y * q.w,
      x * q.w + w * q.x + y * q.z - z * q.y)

  def inverse: Quaternion = {
    val den: Double = normSqr
    Quaternion(x / den, -y / den, -z / den, -w / den)
  }

  override def /(r: Double): Quaternion = Quaternion(x / r, y / r, z / r, w / r)

  def conjugate: Quaternion = Quaternion(x, -y, -z, -w)

  override def sqr: Quaternion = {
    val hy: Double = x * y
    val hz: Double = x * z
    val hw: Double = x * w
    Quaternion(x * x - y * y - z * z - w * w, hy + hy, hz + hz, hw + hw)
  }

  override def cube: Quaternion = {
    val x2: Double = x * x
    val y2: Double = y * y
    val z2: Double = z * z
    val w2: Double = w * w
    val si: Double = y2 + z2 + w2
    Quaternion(x * (x2 - 3.0 * si), (3.0 * x2 - si) * y, (3.0 * x2 - si) * z, (3.0 * x2 - si) * w)
  }


  def iTimes: Quaternion =
    Quaternion(-y, x, -w, z)

  def timesI: Quaternion =
    Quaternion(-y, x, w, -z)

  def jTimes: Quaternion =
    Quaternion(-z, w, x ,-y)

  def timesJ: Quaternion =
    Quaternion(-z, -w, x, y)

  def kTimes: Quaternion =
    Quaternion(-w, -z, y, x)

  def timesK: Quaternion =
    Quaternion(-w, z, -y, x)


  /**
   * A power series `f` with real coefficients as a function on the complex plane can be extended to the quaternions.
   */
  def realPowerSeries(f: Complex => Complex): Quaternion = {
    val in = imagNorm
    val c = Complex(x, imagNorm)
    val fc = f(c)
    if (in == 0d)
      fc.toQuaternion   // f(c) should be real since c is real
    else {
      val q = fc.y / in
      Quaternion(fc.x, y * q, z * q, w * q)
    }
  }


  @inline override def exp: Quaternion = realPowerSeries(_.exp)
  @inline override def log: Quaternion = realPowerSeries(_.log)
  @inline override def log(branch: Int): Quaternion = realPowerSeries(_.log(branch))

  @inline override def sqrt: Quaternion = realPowerSeries(_.sqrt)

  @inline override def sin: Quaternion = realPowerSeries(_.sin)
  @inline override def cos: Quaternion = realPowerSeries(_.cos)
  @inline override def sinh: Quaternion = realPowerSeries(_.sinh)
  @inline override def cosh: Quaternion = realPowerSeries(_.cosh)
  @inline override def tan: Quaternion = realPowerSeries(_.tan)
  @inline override def tanh: Quaternion = realPowerSeries(_.tanh)


  override def toRealVector: RealVector4 =
    RealVector4(Real(x), Real(y), Real(z), Real(w))

  def toJavaQuaternion: JavaQuaternion = new JavaQuaternion(x, y, z, w)


  def toString(nf: NumberFormat,
               parentheses: Boolean = false,
               negativeParentheses: Boolean = false,
               sign: Boolean = false): String = {
    val cnf = new ComplexNumberFormatter(nf, parentheses, negativeParentheses, sign)
    cnf.addNumberString(x)
    cnf.addNumberString(y, "i")
    cnf.addNumberString(z, "j")
    cnf.addNumberString(w, "k")
    cnf.toString
  }

  override def toString: String = toString(NumberFormatHelper.getDefaultNumberFormat)

}


object Quaternion {

  val zero: Quaternion = Quaternion(0.0)
  val one: Quaternion = Quaternion(1.0)
  val i: Quaternion = Quaternion(0.0, 1.0)
  val j: Quaternion = Quaternion(0.0, 0.0, 1.0)
  val k: Quaternion = Quaternion(0.0, 0.0, 0.0, 1.0)
  val minusOne: Quaternion = Quaternion(-1.0)
  val minusI: Quaternion = Quaternion(0.0, -1.0)
  val minusJ: Quaternion = Quaternion(0.0, 0.0, -1.0)
  val minusK: Quaternion = Quaternion(0.0, 0.0, 0.0, -1.0)


  implicit def doubleToQuaternion(x: Double): Quaternion =
    Quaternion(x)

  implicit def realToQuaternion(x: Real): Quaternion =
    Quaternion(x.x)

  implicit def complexToQuaternion(c: Complex): Quaternion =
    Quaternion(c.x, c.y)

  implicit def coordinateToQuaternion(c: Coordinate4D): Quaternion =
    Quaternion(c.getX, c.getY, c.getZ, c.getW)

}

case object NoQuaternion
  extends OptQuaternion
    with NoHigherNumber[OptQuaternion, Quaternion] {

  override def toRealVector: RealVector4 =
    RealVector4(OptReal.none, OptReal.none, OptReal.none, OptReal.none)

  override def toString: String = "na"

}
