package de.gdietz.fun.fractal.scala.util

case class HigherVector0[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]]()
  extends HigherVector[HigherVector0[O, X], O, X] {

  override def const(x: O): HigherVector0[O, X] =
    this

  override def map(f: O => O): HigherVector0[O, X] =
    this

  override def mapOp(x: HigherVector0[O, X])(f: (O, O) => O): HigherVector0[O, X] =
    this

  override def forall(cond: O => Boolean): Boolean =
    true

  override def exists(cond: O => Boolean): Boolean =
    false

  override def filter(cond: O => Boolean): HigherVector0[O, X] =
    this

  override def mapNumber(f: X => O): HigherVector0[O, X] =
    this

  override def mapOpNumber(x: HigherVector0[O, X])(f: (X, X) => O): HigherVector0[O, X] =
    this

  override def filterNumber(cond: X => Boolean): HigherVector0[O, X] =
    this

  override def isAllNumber: Boolean =
    true

  override def existsNumber: Boolean =
    false

  override def isZero: Boolean =
    true

  override def optNormSqr: Option[Double] =
    Some(0.0)

  override def unary_- : HigherVector0[O, X] =
    this

  override def +(x: HigherVector0[O, X]): HigherVector0[O, X] =
    this

  override def -(x: HigherVector0[O, X]): HigherVector0[O, X] =
    this

  override def *(x: HigherVector0[O, X]): HigherVector0[O, X] =
    this

  override def inverse: HigherVector0[O, X] =
    this

  override def /(x: HigherVector0[O, X]): HigherVector0[O, X] =
    this

  override def \(x: HigherVector0[O, X]): HigherVector0[O, X] =
    this

  override def conjugate: HigherVector0[O, X] =
    this

  override def none: HigherVector0[O, X] =
    this

  override def zero: HigherVector0[O, X] =
    this

  override def unit: HigherVector0[O, X] =
    this

  override def sqr: HigherVector0[O, X] =
    this

  override def cube: HigherVector0[O, X] =
    this

  override def pow(n: Int): HigherVector0[O, X] =
    this

  override def +!(x: O): HigherVector0[O, X] =
    this

  override def +!:(x: O): HigherVector0[O, X] =
    this

  override def -!(x: O): HigherVector0[O, X] =
    this

  override def -!:(x: O): HigherVector0[O, X] =
    this

  override def *!(x: O): HigherVector0[O, X] =
    this

  override def *!:(x: O): HigherVector0[O, X] =
    this

  override def /!(x: O): HigherVector0[O, X] =
    this

  override def /!:(x: O): HigherVector0[O, X] =
    this

  override def +(r: Double): HigherVector0[O, X] =
    this

  override def -(r: Double): HigherVector0[O, X] =
    this

  override def -:(r: Double): HigherVector0[O, X] =
    this

  override def *(r: Double): HigherVector0[O, X] =
    this

  override def /(r: Double): HigherVector0[O, X] =
    this

  override def /:(r: Double): HigherVector0[O, X] =
    this


  final override def ::(x: O): O =
    x

  final override def toHigherVectorN: HigherVectorN[O, X] =
    HigherVectorN(Nil)

  final override def get(i: Int): HigherNumberOption[X] =
    HigherNumberNone()


  override def toString: String =
    "()"

}


object RealVector0 {

  @inline def apply(): RealVector0 =
    zero

  val zero: RealVector0 =
    RealVector0()

}

object ComplexVector0 {

  @inline def apply(): ComplexVector0 =
    zero

  val zero: ComplexVector0 =
    ComplexVector0()

}

object QuaternionVector0 {

  @inline def apply(): QuaternionVector0 =
    zero

  val zero: QuaternionVector0 =
    QuaternionVector0()

}

object BigRealVector0 {

  @inline def apply(): BigRealVector0 =
    zero

  val zero: BigRealVector0 =
    BigRealVector0()

}

object BigComplexVector0 {

  @inline def apply(): BigComplexVector0 =
    zero

  val zero: BigComplexVector0 =
    BigComplexVector0()

}
