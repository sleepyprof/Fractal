package de.gdietz.fun.fractal.scala.util

sealed trait HigherNumberOption[X <: HigherNumber[X]]
  extends OptHigherNumber[HigherNumberOption[X], HigherNumberSome[X]]
    with Product with Serializable {

  def isEmptyValue: Boolean
  def isDefinedValue: Boolean

  def getValue: X
  def toOption: Option[X]

  final def getOptValue[O <: OptHigherNumber[O, Y], Y >: X <: O with HigherNumber[Y]](implicit summonable: HigherTupleNoneSummonable[O]): O =
    if (isNumber) getValue: Y else summonable.none

  def foldValue[Y](ifIsDefinedValue: X => Y)(ifIsEmptyValue: => Y): Y

  def mapValue[Y <: HigherNumber[Y]](f: X => Y): HigherNumberOption[Y]
  def mapOpValue[Y <: HigherNumber[Y], Z <: HigherNumber[Z]](y: HigherNumberOption[Y])(f: (X, Y) => Z): HigherNumberOption[Z]

  def forallValue(cond: X => Boolean): Boolean
  def existsValue(cond: X => Boolean): Boolean

  def filterValue(cond: X => Boolean): HigherNumberOption[X]

  override def none: HigherNumberOption[X] = HigherNumberNone()

}

object HigherNumberOption extends HigherNumberOptionInstances

private[util] sealed abstract class HigherNumberOptionInstances extends HigherNumberOptionInstances0 {

  implicit def higherNumberOptionSummonable[X <: HigherNumber[X]]: HigherTupleNoneSummonable[HigherNumberOption[X]] =
    new HigherTupleNoneSummonable[HigherNumberOption[X]] with Serializable {
      override val none: HigherNumberOption[X] = HigherNumberNone()
    }

}

private[util] sealed abstract class HigherNumberOptionInstances0 {

  implicit def higherNumberOptionSummonable[X <: HigherNumber[X]](implicit summonable: HigherNumberSummonable[X]): OptHigherNumberSummonable[HigherNumberOption[X], HigherNumberSome[X]] =
    new OptHigherNumberSummonable[HigherNumberOption[X], HigherNumberSome[X]] with Serializable {
      override val none: HigherNumberOption[X] = HigherNumberNone()
      override val zero: HigherNumberSome[X] = HigherNumberSome(summonable.zero)
      override val unit: HigherNumberSome[X] = HigherNumberSome(summonable.unit)
    }

}

final case class HigherNumberSome[X <: HigherNumber[X]](value: X)
  extends HigherNumberOption[X] with SomeHigherNumber[HigherNumberOption[X], HigherNumberSome[X]] {

  override def isEmptyValue: Boolean = false

  override def isDefinedValue: Boolean = true

  override def getValue: X = value

  override def toOption: Option[X] = Some(value)

  override def foldValue[Y](ifIsDefinedValue: X => Y)(ifIsEmptyValue: => Y): Y =
    ifIsDefinedValue(value)

  override def mapValue[Y <: HigherNumber[Y]](f: X => Y): HigherNumberSome[Y] =
    HigherNumberSome(f(value))

  override def mapOpValue[Y <: HigherNumber[Y], Z <: HigherNumber[Z]](y: HigherNumberOption[Y])(f: (X, Y) => Z): HigherNumberOption[Z] =
    y.mapValue(yValue => f(value, yValue))

  override def forallValue(cond: X => Boolean): Boolean = cond(value)

  override def existsValue(cond: X => Boolean): Boolean = cond(value)

  override def filterValue(cond: X => Boolean): HigherNumberOption[X] =
    if (cond(value)) this else HigherNumberNone()

  override def isZero: Boolean = value.isZero

  override def isUnit: Boolean = value.isUnit

  override def normSqr: Double = value.normSqr

  override def unary_- : HigherNumberSome[X] =
    HigherNumberSome(-value)

  override def +(x: HigherNumberSome[X]): HigherNumberSome[X] =
    HigherNumberSome(value + x.value)

  override def -(x: HigherNumberSome[X]): HigherNumberSome[X] =
    HigherNumberSome(value - x.value)

  override def *(x: HigherNumberSome[X]): HigherNumberSome[X] =
    HigherNumberSome(value * x.value)

  override def inverse: HigherNumberSome[X] =
    HigherNumberSome(value.inverse)

  override def /(x: HigherNumberSome[X]): HigherNumberSome[X] =
    HigherNumberSome(value / x.value)

  override def \(x: HigherNumberSome[X]): HigherNumberSome[X] =
    HigherNumberSome(value \ x.value)

  override def conjugate: HigherNumberSome[X] =
    HigherNumberSome(value.conjugate)

  override def zero: HigherNumberSome[X] =
    HigherNumberSome(value.zero)

  override def unit: HigherNumberSome[X] =
    HigherNumberSome(value.unit)

  override def sqr: HigherNumberSome[X] =
    HigherNumberSome(value.sqr)

  override def cube: HigherNumberSome[X] =
    HigherNumberSome(value.cube)

  override def pow(n: Int): HigherNumberSome[X] =
    HigherNumberSome(value.pow(n))

  override def +(r: Double): HigherNumberSome[X] =
    HigherNumberSome(value + r)

  override def -(r: Double): HigherNumberSome[X] =
    HigherNumberSome(value - r)

  override def -:(r: Double): HigherNumberSome[X] =
    HigherNumberSome(r -: value)

  override def *(r: Double): HigherNumberSome[X] =
    HigherNumberSome(value * r)

  override def /(r: Double): HigherNumberSome[X] =
    HigherNumberSome(value / r)

  override def /:(r: Double): HigherNumberSome[X] =
    HigherNumberSome(r /: value)

}

final case class HigherNumberNone[X <: HigherNumber[X]]()
  extends HigherNumberOption[X] with NoHigherNumber[HigherNumberOption[X], HigherNumberSome[X]] {

  override def isEmptyValue: Boolean = true

  override def isDefinedValue: Boolean = false

  override def getValue: X =
    throw new NoSuchElementException("HigherNumberNone.getValue")

  override def toOption: Option[X] = None

  override def foldValue[Y](ifIsDefinedValue: X => Y)(ifIsEmptyValue: => Y): Y =
    ifIsEmptyValue

  override def mapValue[Y <: HigherNumber[Y]](f: X => Y): HigherNumberOption[Y] =
    HigherNumberNone()

  override def mapOpValue[Y <: HigherNumber[Y], Z <: HigherNumber[Z]](y: HigherNumberOption[Y])(f: (X, Y) => Z): HigherNumberOption[Z] =
    HigherNumberNone()

  override def forallValue(cond: X => Boolean): Boolean = true

  override def existsValue(cond: X => Boolean): Boolean = false

  override def filterValue(cond: X => Boolean): HigherNumberOption[X] = this

  override def zero: HigherNumberOption[X] = this

  override def unit: HigherNumberOption[X] = this

}

object HigherNumberNone {

  private val instance: HigherNumberNone[_] = new HigherNumberNone

  @inline def apply[X <: HigherNumber[X]](): HigherNumberNone[X] =
    instance.asInstanceOf[HigherNumberNone[X]]

}
