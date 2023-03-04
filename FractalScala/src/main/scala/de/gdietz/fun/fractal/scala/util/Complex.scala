package de.gdietz.fun.fractal.scala.util

import de.gdietz.fun.fractal.util.{ComplexNumberFormatter, Coordinate, Complex => JavaComplex}
import de.gdietz.util.NumberFormatHelper

import java.text.NumberFormat
import scala.language.implicitConversions

sealed trait OptComplex
  extends OptHigherNumber[OptComplex, Complex]
    with Product with Serializable {

  override def none: OptComplex = NoComplex
  override def zero: Complex = Complex.zero
  override def unit: Complex = Complex.one

  def toQuaternion: OptQuaternion

  def toRealVector: RealVector2

}

object OptComplex {

  def apply(x: Double, y: Double = 0.0): OptComplex =
    Complex(x, y)

  val none: OptComplex = NoComplex


  implicit def doubleToOptComplex(x: Double): OptComplex =
    Complex(x)

  implicit def optRealToOptComplex(x: OptReal): OptComplex =
    x match {
      case Real(x) => Complex(x)
      case _ => NoComplex
    }

  implicit def coordinateToOptComplex(c: Coordinate): OptComplex =
    Complex(c.getX, c.getY)

}


case class Complex(x: Double, y: Double = 0.0)
  extends OptComplex
    with SomeHigherNumber[OptComplex, Complex]
    with HigherHoloNumber[Complex] {

  override def isZero: Boolean = x == 0.0 && y == 0.0

  override def isUnit: Boolean = x == 1.0 && y == 0.0

  override def normSqr: Double = x * x + y * y

  def arg: Double =
    if (x == 0.0 && y == 0.0) 0.0
    else Math.atan2(y, x)

  override def unary_- : Complex = Complex(-x, -y)

  override def +(r: Double): Complex = Complex(x + r, y)

  override def +(c: Complex): Complex = Complex(x + c.x, y + c.y)

  override def -(r: Double): Complex = Complex(x - r, y)

  override def -:(r: Double): Complex = Complex(r - x, -y)

  override def -(c: Complex): Complex = Complex(x - c.x, y - c.y)

  override def *(r: Double): Complex = Complex(r * x, r * y)

  override def *(c: Complex): Complex = Complex(x * c.x - y * c.y, x * c.y + y * c.x)

  override def inverse: Complex = {
    val den = normSqr
    Complex(x / den, -y / den)
  }

  override def /(r: Double): Complex = Complex(x / r, y / r)

  override def /(c: Complex): Complex = {
    val den = c.normSqr
    Complex((x * c.x + y * c.y) / den, (y * c.x - x * c.y) / den)
  }

  override def conjugate: Complex = Complex(x, -y)

  override def sqr: Complex = {
    val h = x * y
    Complex(x * x - y * y, h + h)
  }

  override def cube: Complex = {
    val x2 = x * x
    val y2 = y * y
    Complex(x * (x2 - 3.0 * y2), (3.0 * x2 - y2) * y)
  }

  override def exp: Complex = {
    val er = Math.exp(x)
    Complex(er * Math.cos(y), er * Math.sin(y))
  }

  override def log: Complex = Complex(Math.log(norm), arg)

  override def log(branch: Int): Complex =
    Complex(Math.log(norm), arg + branch * Complex.pi2)

  @inline override def pow(n: Int): Complex = super.pow(n)

  override def pow(d: Double): Complex =
    if (isZero) this
    else {
      val pr = Math.pow(norm, d)
      val pa = d * arg
      Complex(pr * Math.cos(pa), pr * Math.sin(pa))
    }

  override def pow(d: Double, branch: Int): Complex =
    if (isZero) this
    else {
      val pr = Math.pow(norm, d)
      val pa = d * (arg + branch * Complex.pi2)
      Complex(pr * Math.cos(pa), pr * Math.sin(pa))
    }

  @inline override def **(n: Int): Complex = pow(n)

  override def sqrt: Complex =
    if (y == 0.0) {
      if (x >= 0.0) Complex(Math.sqrt(x))
      else Complex(0.0, Math.sqrt(-x))
    } else {
      val r = norm
      val w = Math.sqrt(2.0 * (x + r))
      Complex(0.5 * w, y / w)
    }

  def roots2: ComplexVector2 = {
    val sqrt0 = sqrt
    ComplexVector2(sqrt0, -sqrt0)
  }

  def roots(n: Int): ComplexVectorN =
    if (isZero) ComplexVectorN(List.fill(n)(Complex.zero))
    else if (n <= 0) ComplexVectorN(Nil)
    else {
      val pr = Math.pow(norm, 1.0 / n)
      val pa = arg / n
      val phi1 = Complex.pi2 / n
      ComplexVectorN((0 until n).view.map { k =>
        val phi = pa + phi1 * k
        Complex(pr * Math.cos(phi), pr * Math.sin(phi))
      }.toList)
    }

  override def sin: Complex =
    Complex(Math.sin(x) * Math.cosh(y), Math.cos(x) * Math.sinh(y))

  override def cos: Complex =
    Complex(Math.cos(x) * Math.cosh(y), -Math.sin(x) * Math.sinh(y))

  override def sinh: Complex =
    Complex(Math.sinh(x) * Math.cos(y), Math.cosh(x) * Math.sin(y))

  override def cosh: Complex =
    Complex(Math.cosh(x) * Math.cos(y), Math.sinh(x) * Math.sin(y))

  override def tan: Complex = {
    val dx = x + x
    val dy = y + y
    val h = Math.cos(dx) + Math.cosh(dy)
    Complex(Math.sin(dx) / h, Math.sinh(dy) / h)
  }

  override def tanh: Complex = {
    val dx = x + x
    val dy = y + y
    val h = Math.cosh(dx) + Math.cos(dy)
    Complex(Math.sinh(dx) / h, Math.sin(dy) / h)
  }


  def iTimes: Complex =
    Complex(-y, x)

  def plusTimesJ(c: Complex): Quaternion =
    Quaternion(x, y, c.x, c.y)


  override def toQuaternion: Quaternion = Quaternion(x, y)

  override def toRealVector: RealVector2 =
    RealVector2(Real(x), Real(y))

  def toJavaComplex: JavaComplex = new JavaComplex(x, y)


  def toString(nf: NumberFormat,
               parentheses: Boolean = false,
               negativeParentheses: Boolean = false,
               sign: Boolean = false): String = {
    val cnf = new ComplexNumberFormatter(nf, parentheses, negativeParentheses, sign)
    cnf.addNumberString(x)
    cnf.addNumberString(y, "i")
    cnf.toString
  }

  override def toString: String = toString(NumberFormatHelper.getDefaultNumberFormat)

}

object Complex {

  val zero: Complex = Complex(0.0)
  val one: Complex = Complex(1.0)
  val i: Complex = Complex(0.0, 1.0)
  val minusOne: Complex = Complex(-1.0)
  val minusI: Complex = Complex(0.0, -1.0)

  private[util] val pi2 = 2.0 * Math.PI


  def fromPolar(r: Double, phi: Double): Complex =
    Complex(r * Math.cos(phi), r * Math.sin(phi))


  implicit def doubleToComplex(x: Double): Complex =
    Complex(x)

  implicit def realToComplex(x: Real): Complex =
    Complex(x.x)

  implicit def coordinateToComplex(c: Coordinate): Complex =
    Complex(c.getX, c.getY)

}

case object NoComplex
  extends OptComplex
    with NoHigherNumber[OptComplex, Complex] {

  override def toQuaternion: OptQuaternion = NoQuaternion

  override def toRealVector: RealVector2 =
    RealVector2(OptReal.none, OptReal.none)

  override def toString: String = "na"

}
