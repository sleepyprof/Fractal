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

  def apply(x: BigDecimal, y: BigDecimal = BigReal.decimalZero): OptBigComplex =
    BigComplex(x, y)

  val none: OptComplex = NoComplex


  implicit def optComplexToOptBigComplex(c: OptComplex): OptBigComplex =
    c match {
      case Complex(x, y) => BigComplex(x, y)
      case _ => NoBigComplex
    }

  implicit def bigDecimalToOptBigComplex(x: BigDecimal): OptBigComplex =
    BigComplex(x)

  implicit def javaBigDecimalToOptBigComplex(x: JavaBigDecimal): OptBigComplex =
    BigComplex(x)

  implicit def doubleToOptBigComplex(x: Double): OptBigComplex =
    BigComplex(x)

  implicit def optRealToOptBigComplex(x: OptReal): OptBigComplex =
    x match {
      case Real(x) => BigComplex(x)
      case _ => NoBigComplex
    }

  implicit def optBigRealOptToBigComplex(x: OptBigReal): OptBigComplex =
    x match {
      case BigReal(x) => BigComplex(x)
      case _ => NoBigComplex
    }

  implicit def bigCoordinateToOptBigComplex(c: BigCoordinate): OptBigComplex =
    BigComplex(c.getX, c.getY)

  implicit def coordinateToOptBigComplex(c: Coordinate): OptBigComplex =
    BigComplex(c.getX, c.getY)

}


case class BigComplex(x: BigDecimal, y: BigDecimal = BigReal.decimalZero)
  extends OptBigComplex
    with SomeHigherNumber[OptBigComplex, BigComplex] {

  override def isZero: Boolean = x == BigReal.decimalZero && y == BigReal.decimalZero

  override def isUnit: Boolean = x == BigReal.decimalOne && y == BigReal.decimalZero

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
    BigComplex(x * (x2 - BigReal.decimalThree * y2), (BigReal.decimalThree * x2 - y2) * y)
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

  val zero: BigComplex = BigComplex(BigReal.decimalZero)
  val one: BigComplex = BigComplex(BigReal.decimalOne)
  val i: BigComplex = BigComplex(BigReal.decimalZero, BigReal.decimalOne)
  val minusOne: BigComplex = BigComplex(BigReal.decimalMinusOne)
  val minusI: BigComplex = BigComplex(BigReal.decimalZero, BigReal.decimalMinusOne)


  implicit def complexToBigComplex(c: Complex): BigComplex =
    BigComplex(c.x, c.y)

  implicit def bigDecimalToBigComplex(x: BigDecimal): BigComplex =
    BigComplex(x)

  implicit def javaBigDecimalToBigComplex(x: JavaBigDecimal): BigComplex =
    BigComplex(x)

  implicit def doubleToBigComplex(x: Double): BigComplex =
    BigComplex(x)

  implicit def realToBigComplex(x: Real): BigComplex =
    BigComplex(x.x)

  implicit def bigRealToBigComplex(x: BigReal): BigComplex =
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
