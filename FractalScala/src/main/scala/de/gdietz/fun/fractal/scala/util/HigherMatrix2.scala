package de.gdietz.fun.fractal.scala.util

case class HigherMatrix2[X <: HigherNumber[X]](x11: X, x12: X, x21: X, x22: X)
  extends HigherMatrix[HigherMatrix2[X], X] {

  override def isZero: Boolean =
    x11.isZero && x12.isZero && x21.isZero && x22.isZero

  override def isUnit: Boolean =
    x11.isUnit && x12.isZero && x21.isZero && x22.isUnit

  override def normSqr: Double =
    x11.normSqr + x12.normSqr + x21.normSqr + x22.normSqr


  override def unary_- : HigherMatrix2[X] =
    HigherMatrix2(-x11, -x12, -x21, -x22)

  override def +(x: HigherMatrix2[X]): HigherMatrix2[X] =
    HigherMatrix2(x11 + x.x11, x12 + x.x12, x21 + x.x21, x22 + x.x22)

  override def -(x: HigherMatrix2[X]): HigherMatrix2[X] =
    HigherMatrix2(x11 - x.x11, x12 - x.x12, x21 - x.x21, x22 - x.x22)

  override def *(x: HigherMatrix2[X]): HigherMatrix2[X] =
    HigherMatrix2(
      x11 * x.x11 + x12 * x.x21,
      x11 * x.x12 + x12 * x.x22,
      x21 * x.x11 + x22 * x.x21,
      x21 * x.x12 + x22 * x.x22)

  override def trace: X =
    x11 + x22

  override def transpose: HigherMatrix2[X] =
    HigherMatrix2(x11, x21, x12, x22)

  // for a commutative field this could be easier (and faster and probably numerically more stable)
  override def inverse: HigherMatrix2[X] = {
    val x12Inv = x12.inverse
    val x22Inv = x22.inverse
    val inv11 = (x11 - x12 * x22Inv * x21).inverse
    val inv12 = (x21 - x22 * x12Inv * x11).inverse
    HigherMatrix2(
      inv11,
      inv12,
      -x22Inv * x21 * inv11,
      -x12Inv * x11 * inv12)
  }

  override def conjugate: HigherMatrix2[X] =
    HigherMatrix2(x11.conjugate, x12.conjugate, x21.conjugate, x22.conjugate)

  override def zero: HigherMatrix2[X] =
    HigherMatrix2(x11.zero, x12.zero, x21.zero, x22.zero)

  override def unit: HigherMatrix2[X] =
    HigherMatrix2(x11.unit, x12.zero, x21.zero, x22.unit)

  override def *!(x: X): HigherMatrix2[X] =
    HigherMatrix2(x11 * x, x12 * x, x21 * x, x22 * x)

  override def *!:(x: X): HigherMatrix2[X] =
    HigherMatrix2(x * x11, x * x12, x * x21, x * x22)

  override def /!(x: X): HigherMatrix2[X] =
    HigherMatrix2(x11 / x, x12 / x, x21 / x, x22 / x)

  override def +(r: Double): HigherMatrix2[X] =
    HigherMatrix2(x11 + r, x12, x21, x22 + r)

  override def -(r: Double): HigherMatrix2[X] =
    HigherMatrix2(x11 - r, x12, x21, x22 - r)

  override def -:(r: Double): HigherMatrix2[X] =
    HigherMatrix2(r -: x11, -x12, -x21, r -: x22)

  override def *(r: Double): HigherMatrix2[X] =
    HigherMatrix2(x11 * r, x12 * r, x21 * r, x22 * r)

  override def /(r: Double): HigherMatrix2[X] =
    HigherMatrix2(x11 / r, x12 / r, x21 / r, x22 / r)


  override def toString: String =
    "(" + x11 + ", " + x12 + " | " + x21 + ", " + x22 + ")"

}


object RealMatrix2 {

  @inline def apply(x11: Real, x12: Real, x21: Real, x22: Real): RealMatrix2 =
    HigherMatrix2(x11, x12, x21, x22)

  def unapply(x: RealMatrix2): Option[(Real, Real, Real, Real)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: RealMatrix2 =
    RealMatrix2(Real.zero, Real.zero, Real.zero, Real.zero)

  val unit: RealMatrix2 =
    RealMatrix2(Real.one, Real.zero, Real.zero, Real.one)

  val pauli1: RealMatrix2 =
    RealMatrix2(Real.zero, Real.one, Real.one, Real.zero)

  val iPauli2: RealMatrix2 =
    RealMatrix2(Real.zero, Real.one, Real.minusOne, Real.zero)

  val pauli3: RealMatrix2 =
    RealMatrix2(Real.one, Real.zero, Real.zero, Real.minusOne)

}

object ComplexMatrix2 {

  @inline def apply(x11: Complex, x12: Complex, x21: Complex, x22: Complex): ComplexMatrix2 =
    HigherMatrix2(x11, x12, x21, x22)

  def unapply(x: ComplexMatrix2): Option[(Complex, Complex, Complex, Complex)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: ComplexMatrix2 =
    ComplexMatrix2(Complex.zero, Complex.zero, Complex.zero, Complex.zero)

  val unit: ComplexMatrix2 =
    ComplexMatrix2(Complex.one, Complex.zero, Complex.zero, Complex.one)

  val pauli1: ComplexMatrix2 =
    ComplexMatrix2(Complex.zero, Complex.one, Complex.one, Complex.zero)

  val pauli2: ComplexMatrix2 =
    ComplexMatrix2(Complex.zero, Complex.minusI, Complex.i, Complex.zero)

  val pauli3: ComplexMatrix2 =
    ComplexMatrix2(Complex.one, Complex.zero, Complex.zero, Complex.minusOne)

  def pauli(xu: Complex, x1: Complex, x2: Complex, x3: Complex): ComplexMatrix2 =
    ComplexMatrix2(xu + x3, x1 - Complex.i * x2, x1 + Complex.i * x2, xu - x3)

}

object QuaternionMatrix2 {

  @inline def apply(x11: Quaternion, x12: Quaternion, x21: Quaternion, x22: Quaternion): QuaternionMatrix2 =
    HigherMatrix2(x11, x12, x21, x22)

  def unapply(x: QuaternionMatrix2): Option[(Quaternion, Quaternion, Quaternion, Quaternion)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: QuaternionMatrix2 =
    QuaternionMatrix2(Quaternion.zero, Quaternion.zero, Quaternion.zero, Quaternion.zero)

  val unit: QuaternionMatrix2 =
    QuaternionMatrix2(Quaternion.one, Quaternion.zero, Quaternion.zero, Quaternion.one)

}

object BigRealMatrix2 {

  @inline def apply(x11: BigReal, x12: BigReal, x21: BigReal, x22: BigReal): BigRealMatrix2 =
    HigherMatrix2(x11, x12, x21, x22)

  def unapply(x: BigRealMatrix2): Option[(BigReal, BigReal, BigReal, BigReal)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: BigRealMatrix2 =
    BigRealMatrix2(BigReal.zero, BigReal.zero, BigReal.zero, BigReal.zero)

  val unit: BigRealMatrix2 =
    BigRealMatrix2(BigReal.one, BigReal.zero, BigReal.zero, BigReal.one)

  val pauli1: BigRealMatrix2 =
    BigRealMatrix2(BigReal.zero, BigReal.one, BigReal.one, BigReal.zero)

  val iPauli2: BigRealMatrix2 =
    BigRealMatrix2(BigReal.zero, BigReal.one, BigReal.minusOne, BigReal.zero)

  val pauli3: BigRealMatrix2 =
    BigRealMatrix2(BigReal.one, BigReal.zero, BigReal.zero, BigReal.minusOne)

}

object BigComplexMatrix2 {

  @inline def apply(x11: BigComplex, x12: BigComplex, x21: BigComplex, x22: BigComplex): BigComplexMatrix2 =
    HigherMatrix2(x11, x12, x21, x22)

  def unapply(x: BigComplexMatrix2): Option[(BigComplex, BigComplex, BigComplex, BigComplex)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: BigComplexMatrix2 =
    BigComplexMatrix2(BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.zero)

  val unit: BigComplexMatrix2 =
    BigComplexMatrix2(BigComplex.one, BigComplex.zero, BigComplex.zero, BigComplex.one)

  val pauli1: BigComplexMatrix2 =
    BigComplexMatrix2(BigComplex.zero, BigComplex.one, BigComplex.one, BigComplex.zero)

  val pauli2: BigComplexMatrix2 =
    BigComplexMatrix2(BigComplex.zero, BigComplex.minusI, BigComplex.i, BigComplex.zero)

  val pauli3: BigComplexMatrix2 =
    BigComplexMatrix2(BigComplex.one, BigComplex.zero, BigComplex.zero, BigComplex.minusOne)

  def pauli(xu: BigComplex, x1: BigComplex, x2: BigComplex, x3: BigComplex): BigComplexMatrix2 =
    BigComplexMatrix2(xu + x3, x1 - BigComplex.i * x2, x1 + BigComplex.i * x2, xu - x3)

}
