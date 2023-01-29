package de.gdietz.fun.fractal.scala.util

import de.gdietz.fun.fractal.util.ComplexNumberFormatter
import de.gdietz.util.NumberFormatHelper

import java.text.NumberFormat
import scala.language.implicitConversions

sealed trait OptReal
  extends OptHigherNumber[OptReal, Real]
    with Product with Serializable {

  override def none: OptReal = NoReal
  override def zero: Real = Real.zero
  override def unit: Real = Real.one

  def toComplex: OptComplex

  def toQuaternion: OptQuaternion

}

object OptReal {

  def apply(x: Double): OptReal =
    Real(x)

  val none: OptReal = NoReal


  implicit def doubleToOptReal(x: Double): OptReal =
    Real(x)

}


case class Real(x: Double)
  extends OptReal
    with SomeHigherNumber[OptReal, Real]
    with HigherHoloNumber[Real] {

  override def isZero: Boolean = x == 0.0

  override def isUnit: Boolean = x == 1.0

  override def normSqr: Double = x * x

  override def norm: Double = Math.abs(x)

  override def unary_- : Real = Real(-x)

  override def +(r: Double): Real = Real(x + r)

  override def +(c: Real): Real = Real(x + c.x)

  override def -(r: Double): Real = Real(x - r)

  override def -:(r: Double): Real = Real(r - x)

  override def -(c: Real): Real = Real(x - c.x)

  override def *(r: Double): Real = Real(r * x)

  override def *(c: Real): Real = Real(x * c.x)

  override def inverse: Real = Real(1.0 / x)

  override def /(r: Double): Real = Real(x / r)

  override def /(c: Real): Real = Real(x / c.x)

  override def /:(r: Double): Real = Real(r / x)

  override def conjugate: Real = this

  override def sqr: Real = Real(x * x)

  override def cube: Real = Real(x * x * x)

  override def exp: Real = Real(Math.exp(x))

  override def log: Real = Real(Math.log(x))

  override def log(branch: Int): Real = Real(Math.log(x))

  @inline override def pow(n: Int): Real = super.pow(n)

  override def pow(c: Real): Real = Real(Math.pow(x, c.x))

  override def pow(d: Double): Real = Real(Math.pow(x, d))

  @inline override def **(n: Int): Real = pow(n)

  override def sqrt: Real = Real(Math.sqrt(x))

  override def sin: Real = Real(Math.sin(x))

  override def cos: Real = Real(Math.cos(x))

  override def sinh: Real = Real(Math.sinh(x))

  override def cosh: Real = Real(Math.cosh(x))

  override def tan: Real = Real(Math.tan(x))

  override def tanh: Real = Real(Math.tanh(x))


  def toDouble: Double = x

  override def toComplex: Complex = Complex(x)

  override def toQuaternion: Quaternion = Quaternion(x)


  def toString(nf: NumberFormat,
               parentheses: Boolean = false,
               negativeParentheses: Boolean = false,
               sign: Boolean = false): String = {
    val cnf = new ComplexNumberFormatter(nf, parentheses, negativeParentheses, sign)
    cnf.addNumberString(x)
    cnf.toString
  }

  override def toString: String = toString(NumberFormatHelper.getDefaultNumberFormat)

}

object Real {

  val zero: Real = Real(0.0)
  val one: Real = Real(1.0)
  val minusOne: Real = Real(-1.0)


  implicit def doubleToReal(x: Double): Real =
    Real(x)

}

case object NoReal
  extends OptReal
    with NoHigherNumber[OptReal, Real] {

  override def toComplex: OptComplex = NoComplex

  override def toQuaternion: OptQuaternion = NoQuaternion

  override def toString: String = "na"

}
