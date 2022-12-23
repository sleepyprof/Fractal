package de.gdietz.fun.fractal.scala.util

import de.gdietz.fun.fractal.util.{BigComplexNumberFormatter, BigCoordinate, Coordinate, BigComplex => JavaBigComplex}

import java.math.{BigDecimal => JavaBigDecimal}
import scala.language.implicitConversions

sealed trait OptBigComplex
  extends OptHigherNumber[OptBigComplex, BigComplex]
    with Product with Serializable {

  override def none: OptBigComplex = NoBigComplex
  override def zero: BigComplex = BigComplex.zero
  override def unit: BigComplex = BigComplex.one

}

object OptBigComplex {

  def apply(x: BigDecimal, y: BigDecimal = BigComplex.decimalZero): OptBigComplex =
    BigComplex(x, y)

  val none: OptComplex = NoComplex


  implicit def doubleToOptComplex(x: Double): OptComplex =
    Complex(x)

  implicit def bigCoordinateToOptBigComplex(c: BigCoordinate): OptBigComplex =
    BigComplex(c.getX, c.getY)

}


case class BigComplex(x: BigDecimal, y: BigDecimal = BigComplex.decimalZero)
  extends OptBigComplex
    with SomeHigherNumber[OptBigComplex, BigComplex] {

  override def isZero: Boolean = x == BigComplex.decimalZero && y == BigComplex.decimalZero

  override def isUnit: Boolean = x == BigComplex.decimalOne && y == BigComplex.decimalZero

  def normSqrPrecise: BigDecimal = x * x + y * y

  @inline override def normSqr: Double = normSqrPrecise.toDouble

  override def unary_- : BigComplex = BigComplex(-x, -y)

  override def +(r: Double): BigComplex = {
    val r0 = BigDecimal.decimal(r)
    BigComplex(x + r0, y)
  }

  override def +(c: BigComplex): BigComplex = BigComplex(x + c.x, y + c.y)

  override def -(r: Double): BigComplex = {
    val r0 = BigDecimal.decimal(r)
    BigComplex(x - r0, y)
  }

  override def -:(r: Double): BigComplex = {
    val r0 = BigDecimal.decimal(r)
    BigComplex(r0 - x, -y)
  }

  override def -(c: BigComplex): BigComplex = BigComplex(x - c.x, y - c.y)

  override def *(r: Double): BigComplex = {
    val r0 = BigDecimal.decimal(r)
    BigComplex(r0 * x, r0 * y)
  }

  override def *(c: BigComplex): BigComplex = BigComplex(x * c.x - y * c.y, x * c.y + y * c.x)

  override def inverse: BigComplex = {
    val den = normSqrPrecise
    BigComplex(x / den, -y / den)
  }

  override def /(r: Double): BigComplex = {
    val r0 = BigDecimal.decimal(r)
    BigComplex(x / r0, y / r0)
  }

  override def /(c: BigComplex): BigComplex = {
    val den = c.normSqrPrecise
    BigComplex((x * c.x + y * c.y) / den, (y * c.x - x * c.y) / den)
  }

  override def conjugate: BigComplex = BigComplex(x, -y)

  override def sqr: BigComplex = {
    val h = x * y
    BigComplex(x * x - y * y, h + h)
  }

  override def cube: BigComplex = {
    val x2 = x * x
    val y2 = y * y
    BigComplex(x * (x2 - BigComplex.decimalThree * y2), (BigComplex.decimalThree * x2 - y2) * y)
  }

  @inline override def pow(n: Int): BigComplex = super.pow(n)

  @inline override def **(n: Int): BigComplex = pow(n)


  def toJavaBigComplex: JavaBigComplex = new JavaBigComplex(x.bigDecimal, y.bigDecimal)


  def toString(parentheses: Boolean = false,
               negativeParentheses: Boolean = false,
               sign: Boolean = false): String = {
    val cnf = new BigComplexNumberFormatter(parentheses, negativeParentheses, sign)
    cnf.addNumberString(x.bigDecimal)
    cnf.addNumberString(y.bigDecimal, "i")
    cnf.toString
  }

  override def toString: String = toString(parentheses = false)

}

object BigComplex {

  val decimalZero: BigDecimal = JavaBigDecimal.ZERO
  val decimalOne: BigDecimal = JavaBigDecimal.ONE
  val decimalMinusOne: BigDecimal = BigDecimal.decimal(-1)
  val decimalThree: BigDecimal = BigDecimal.decimal(3)

  val zero: BigComplex = BigComplex(decimalZero)
  val one: BigComplex = BigComplex(decimalOne)
  val i: BigComplex = BigComplex(decimalZero, decimalOne)
  val minusOne: BigComplex = BigComplex(decimalMinusOne)
  val minusI: BigComplex = BigComplex(decimalZero, decimalMinusOne)


  implicit def complexToBigComplex(x: Complex): BigComplex =
    BigComplex(x.x, x.y)

  implicit def bigDecimalToBigComplex(x: BigDecimal): BigComplex =
    BigComplex(x)

  implicit def doubleToBigComplex(x: Double): BigComplex =
    BigComplex(x)

  implicit def realToBigComplex(x: Real): BigComplex =
    BigComplex(x.x)

  implicit def bigCoordinateToBigComplex(c: BigCoordinate): BigComplex =
    BigComplex(c.getX, c.getY)

  implicit def coordinateToBigComplex(c: Coordinate): BigComplex =
    BigComplex(c.getX, c.getY)

}

case object NoBigComplex
  extends OptBigComplex
    with NoHigherNumber[OptBigComplex, BigComplex] {

  override def toString: String = "na"

}
