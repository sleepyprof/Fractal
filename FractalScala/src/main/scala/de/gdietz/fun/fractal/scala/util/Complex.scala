package de.gdietz.fun.fractal.scala.util

import de.gdietz.fun.fractal.util.{ComplexNumberFormatter, Coordinate, Complex => JavaComplex}

import java.text.NumberFormat
import scala.language.implicitConversions

sealed trait OptComplex
  extends OptHigherNumber[OptComplex, Complex]
    with Product with Serializable {

  override val none: OptComplex = NoComplex
  override val zero: Complex = Complex.zero
  override val unit: Complex = Complex.one

  def toRealVector: RealVector2

}

object OptComplex {

  def apply(x: Double, y: Double = 0.0): OptComplex =
    Complex(x, y)

  val none: OptComplex = NoComplex


  implicit def doubleToOptComplex(x: Double): OptComplex =
    Complex(x)

  implicit def coordinateToOptComplex(c: Coordinate): OptComplex =
    Complex(c.getX, c.getY)

}


case class Complex(x: Double, y: Double = 0.0)
  extends OptComplex
    with SomeHigherNumber[OptComplex, Complex] {

  override def isZero: Boolean = x == 0.0 && y == 0.0

  override def isUnit: Boolean = x == 1.0 && y == 0.0

  override def normSqr: Double = x * x + y * y

  def arg: Double = {
    if (x == 0.0 && y == 0.0) return 0.0
    Math.atan2(y, x)
  }

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

  def exp: Complex = {
    val er = Math.exp(x)
    Complex(er * Math.cos(y), er * Math.sin(y))
  }

  def log: Complex = Complex(Math.log(norm), arg)

  def pow(c: Complex): Complex = {
    if (x == 0.0 && y == 0.0) return Complex.zero
    (log * c).exp
  }

  def pow(d: Double): Complex = {
    if (x == 0.0 && y == 0.0) return Complex.zero
    val pr = Math.pow(norm, d)
    val pa = d * arg
    Complex(pr * Math.cos(pa), pr * Math.sin(pa))
  }

  @inline def **(c: Complex): Complex = pow(c)

  @inline def **(d: Double): Complex = pow(d)

  def sqrt: Complex = {
    if (y == 0.0) {
      if (x >= 0.0) Complex(Math.sqrt(x))
      else Complex(0.0, Math.sqrt(-x))
    } else {
      val r = norm
      val w = Math.sqrt(2.0 * (x + r))
      Complex(0.5 * w, y / w)
    }
  }

  def sin: Complex =
    Complex(Math.sin(x) * Math.cosh(y), Math.cos(x) * Math.sinh(y))

  def cos: Complex =
    Complex(Math.cos(x) * Math.cosh(y), -Math.sin(x) * Math.sinh(y))

  def sinh: Complex =
    Complex(Math.sinh(x) * Math.cos(y), Math.cosh(x) * Math.sin(y))

  def cosh: Complex =
    Complex(Math.cosh(x) * Math.cos(y), Math.sinh(x) * Math.sin(y))

  def tan: Complex = {
    val dx = x + x
    val dy = y + y
    val h = Math.cos(dx) + Math.cosh(dy)
    Complex(Math.sin(dx) / h, Math.sinh(dy) / h)
  }

  def tanh: Complex = {
    val dx = x + x
    val dy = y + y
    val h = Math.cosh(dx) + Math.cos(dy)
    Complex(Math.sinh(dx) / h, Math.sin(dy) / h)
  }


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

  override def toString: String = toString(NumberFormat.getInstance)

}

object Complex {

  val zero: Complex = Complex(0.0)
  val one: Complex = Complex(1.0)
  val i: Complex = Complex(0.0, 1.0)
  val minusOne: Complex = Complex(-1.0)

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

  override def toRealVector: RealVector2 =
    RealVector2(OptReal.none, OptReal.none)

  override def toString: String = "na"

}
