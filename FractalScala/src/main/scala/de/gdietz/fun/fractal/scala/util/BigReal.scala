package de.gdietz.fun.fractal.scala.util

import de.gdietz.fun.fractal.util.BigComplexNumberFormatter

import java.math.{BigDecimal => JavaBigDecimal}
import scala.language.implicitConversions

sealed trait OptBigReal
  extends OptHigherNumber[OptBigReal, BigReal]
    with Product with Serializable {

  override def none: OptBigReal = NoBigReal
  override def zero: BigReal = BigReal.zero
  override def unit: BigReal = BigReal.one

  def toBigComplex: OptBigComplex

}

object OptBigReal {

  def apply(x: BigDecimal): OptBigReal =
    BigReal(x)

  val none: OptReal = NoReal


  implicit def optRealToOptBigReal(x: OptReal): OptBigReal =
    x match {
      case Real(x) => BigReal(x)
      case NoReal => NoBigReal
    }

  implicit def bigDecimalToOptBigReal(x: BigDecimal): OptBigReal =
    BigReal(x)

  implicit def javaBigDecimalToOptBigReal(x: JavaBigDecimal): OptBigReal =
    BigReal(x)

  implicit def doubleToOptBigReal(x: Double): OptBigReal =
    BigReal(x)

}


case class BigReal(x: BigDecimal)
  extends OptBigReal
    with SomeHigherNumber[OptBigReal, BigReal] {

  override def isZero: Boolean = x == BigReal.decimalZero

  override def isUnit: Boolean = x == BigReal.decimalOne

  def normSqrPrecise: BigDecimal = x * x

  def normPrecise: BigDecimal = x.abs

  @inline override def normSqr: Double = normSqrPrecise.toDouble

  override def norm: Double = Math.abs(x.toDouble)

  override def unary_- : BigReal = BigReal(-x)

  override def +(r: Double): BigReal = {
    val r0 = BigDecimal.decimal(r)
    BigReal(x + r0)
  }

  override def +(c: BigReal): BigReal = BigReal(x + c.x)

  override def -(r: Double): BigReal = {
    val r0 = BigDecimal.decimal(r)
    BigReal(x - r0)
  }

  override def -:(r: Double): BigReal = {
    val r0 = BigDecimal.decimal(r)
    BigReal(r0 - x)
  }

  override def -(c: BigReal): BigReal = BigReal(x - c.x)

  override def *(r: Double): BigReal = {
    val r0 = BigDecimal.decimal(r)
    BigReal(r0 * x)
  }

  override def *(c: BigReal): BigReal = BigReal(x * c.x)

  override def inverse: BigReal = BigReal(BigReal.decimalOne / x)

  override def /(r: Double): BigReal = {
    val r0 = BigDecimal.decimal(r)
    BigReal(x / r0)
  }

  override def /(c: BigReal): BigReal = BigReal(x / c.x)

  override def conjugate: BigReal = this

  override def sqr: BigReal = BigReal(x * x)

  override def cube: BigReal = BigReal(x * x * x)

  @inline override def pow(n: Int): BigReal = super.pow(n)

  @inline override def **(n: Int): BigReal = pow(n)


  def toBigDecimal: BigDecimal = x

  def toJavaBigDecimal: JavaBigDecimal = x.bigDecimal

  def toDouble: Double = x.toDouble

  def toBigComplex: BigComplex = BigComplex(x)


  def toString(parentheses: Boolean = false,
               negativeParentheses: Boolean = false,
               sign: Boolean = false): String = {
    val cnf = new BigComplexNumberFormatter(parentheses, negativeParentheses, sign)
    cnf.addNumberString(x.bigDecimal)
    cnf.toString
  }

  override def toString: String = toString(parentheses = false)

}

object BigReal {

  val decimalZero: BigDecimal = JavaBigDecimal.ZERO
  val decimalOne: BigDecimal = JavaBigDecimal.ONE
  val decimalMinusOne: BigDecimal = BigDecimal.decimal(-1)
  val decimalThree: BigDecimal = BigDecimal.decimal(3)

  val zero: BigReal = BigReal(decimalZero)
  val one: BigReal = BigReal(decimalOne)
  val minusOne: BigReal = BigReal(decimalMinusOne)


  implicit def realToBigReal(x: Real): BigReal =
    BigReal(x.x)

  implicit def bigDecimalToBigReal(x: BigDecimal): BigReal =
    BigReal(x)

  implicit def javaBigDecimalToBigReal(x: JavaBigDecimal): BigReal =
    BigReal(x)

  implicit def doubleToBigReal(x: Double): BigReal =
    BigReal(x)

}

case object NoBigReal
  extends OptBigReal
    with NoHigherNumber[OptBigReal, BigReal] {

  override def toBigComplex: OptBigComplex = NoBigComplex

  override def toString: String = "na"

}
