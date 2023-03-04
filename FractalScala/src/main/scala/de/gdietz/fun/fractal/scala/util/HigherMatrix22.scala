package de.gdietz.fun.fractal.scala.util

case class HigherMatrix22[X <: HigherNumber[X]](x11: X, x12: X, x21: X, x22: X)
  extends HigherMatrix[HigherMatrix22[X], X] {

  override def isZero: Boolean =
    x11.isZero && x12.isZero && x21.isZero && x22.isZero

  override def normSqr: Double =
    x11.normSqr + x12.normSqr + x21.normSqr + x22.normSqr


  override def unary_- : HigherMatrix22[X] =
    HigherMatrix22(-x11, -x12, -x21, -x22)

  override def +(x: HigherMatrix22[X]): HigherMatrix22[X] =
    HigherMatrix22(x11 + x.x11, x12 + x.x12, x21 + x.x21, x22 + x.x22)

  override def -(x: HigherMatrix22[X]): HigherMatrix22[X] =
    HigherMatrix22(x11 - x.x11, x12 - x.x12, x21 - x.x21, x22 - x.x22)

  override def *(x: HigherMatrix22[X]): HigherMatrix22[X] =
    HigherMatrix22(
      x11 * x.x11 + x12 * x.x21,
      x11 * x.x12 + x12 * x.x22,
      x21 * x.x11 + x22 * x.x21,
      x21 * x.x12 + x22 * x.x22)

  override def trace: X =
    x11 + x22

  // for a commutative field this could be easier (and faster and probably numerically more stable)
  override def inverse: HigherMatrix22[X] = {
    val x12Inv = x12.inverse
    val x22Inv = x22.inverse
    val inv11 = (x11 - x12 * x22Inv * x21).inverse
    val inv12 = (x21 - x22 * x12Inv * x11).inverse
    HigherMatrix22(
      inv11,
      inv12,
      -x22Inv * x21 * inv11,
      -x12Inv * x11 * inv12)
  }

  override def conjugate: HigherMatrix22[X] =
    HigherMatrix22(x11.conjugate, x12.conjugate, x21.conjugate, x22.conjugate)

  override def zero: HigherMatrix22[X] =
    HigherMatrix22(x11.zero, x12.zero, x21.zero, x22.zero)

  override def unit: HigherMatrix22[X] =
    HigherMatrix22(x11.unit, x12.zero, x21.zero, x22.unit)

  override def *!(x: X): HigherMatrix22[X] =
    HigherMatrix22(x11 * x, x12 * x, x21 * x, x22 * x)

  override def *!:(x: X): HigherMatrix22[X] =
    HigherMatrix22(x * x11, x * x12, x * x21, x * x22)

  override def /!(x: X): HigherMatrix22[X] =
    HigherMatrix22(x11 * x, x12 * x, x21 * x, x22 * x)

  override def +(r: Double): HigherMatrix22[X] =
    HigherMatrix22(x11 + r, x12, x21, x22 + r)

  override def -(r: Double): HigherMatrix22[X] =
    HigherMatrix22(x11 - r, x12, x21, x22 - r)

  override def -:(r: Double): HigherMatrix22[X] =
    HigherMatrix22(r -: x11, x12, x21, r -: x22)

  override def *(r: Double): HigherMatrix22[X] =
    HigherMatrix22(x11 * r, x12 * r, x21 * r, x22 * r)

  override def /(r: Double): HigherMatrix22[X] =
    HigherMatrix22(x11 / r, x12 / r, x21 / r, x22 / r)


  override def toString: String =
    "(" + x11 + ", " + x12 + " | " + x21 + ", " + x22 + ")"

}


object RealMatrix22 {

  @inline def apply(x11: Real, x12: Real, x21: Real, x22: Real): RealMatrix22 =
    HigherMatrix22(x11, x12, x21, x22)

  def unapply(x: RealMatrix22): Option[(Real, Real, Real, Real)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: RealMatrix22 =
    RealMatrix22(Real.zero, Real.zero, Real.zero, Real.zero)

  val unit: RealMatrix22 =
    RealMatrix22(Real.one, Real.zero, Real.zero, Real.one)

  val pauli1: RealMatrix22 =
    RealMatrix22(Real.zero, Real.one, Real.one, Real.zero)

  val iPauli2: RealMatrix22 =
    RealMatrix22(Real.zero, Real.one, Real.minusOne, Real.zero)

  val pauli3: RealMatrix22 =
    RealMatrix22(Real.one, Real.zero, Real.zero, Real.minusOne)

}

object ComplexMatrix22 {

  @inline def apply(x11: Complex, x12: Complex, x21: Complex, x22: Complex): ComplexMatrix22 =
    HigherMatrix22(x11, x12, x21, x22)

  def unapply(x: ComplexMatrix22): Option[(Complex, Complex, Complex, Complex)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: ComplexMatrix22 =
    ComplexMatrix22(Complex.zero, Complex.zero, Complex.zero, Complex.zero)

  val unit: ComplexMatrix22 =
    ComplexMatrix22(Complex.one, Complex.zero, Complex.zero, Complex.one)

  val pauli1: ComplexMatrix22 =
    ComplexMatrix22(Complex.zero, Complex.one, Complex.one, Complex.zero)

  val pauli2: ComplexMatrix22 =
    ComplexMatrix22(Complex.zero, Complex.minusI, Complex.i, Complex.zero)

  val pauli3: ComplexMatrix22 =
    ComplexMatrix22(Complex.one, Complex.zero, Complex.zero, Complex.minusOne)

  def pauli(x0: Complex, x1: Complex, x2: Complex, x3: Complex): ComplexMatrix22 =
    ComplexMatrix22(x0 + x3, x1 - Complex.i * x2, x1 + Complex.i * x2, x0 - x3)

}

object QuaternionMatrix22 {

  @inline def apply(x11: Quaternion, x12: Quaternion, x21: Quaternion, x22: Quaternion): QuaternionMatrix22 =
    HigherMatrix22(x11, x12, x21, x22)

  def unapply(x: QuaternionMatrix22): Option[(Quaternion, Quaternion, Quaternion, Quaternion)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: QuaternionMatrix22 =
    QuaternionMatrix22(Quaternion.zero, Quaternion.zero, Quaternion.zero, Quaternion.zero)

  val unit: QuaternionMatrix22 =
    QuaternionMatrix22(Quaternion.one, Quaternion.zero, Quaternion.zero, Quaternion.one)

}

object BigRealMatrix22 {

  @inline def apply(x11: BigReal, x12: BigReal, x21: BigReal, x22: BigReal): BigRealMatrix22 =
    HigherMatrix22(x11, x12, x21, x22)

  def unapply(x: BigRealMatrix22): Option[(BigReal, BigReal, BigReal, BigReal)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: BigRealMatrix22 =
    BigRealMatrix22(BigReal.zero, BigReal.zero, BigReal.zero, BigReal.zero)

  val unit: BigRealMatrix22 =
    BigRealMatrix22(BigReal.one, BigReal.zero, BigReal.zero, BigReal.one)

  val pauli1: BigRealMatrix22 =
    BigRealMatrix22(BigReal.zero, BigReal.one, BigReal.one, BigReal.zero)

  val iPauli2: BigRealMatrix22 =
    BigRealMatrix22(BigReal.zero, BigReal.one, BigReal.minusOne, BigReal.zero)

  val pauli3: BigRealMatrix22 =
    BigRealMatrix22(BigReal.one, BigReal.zero, BigReal.zero, BigReal.minusOne)

}

object BigComplexMatrix22 {

  @inline def apply(x11: BigComplex, x12: BigComplex, x21: BigComplex, x22: BigComplex): BigComplexMatrix22 =
    HigherMatrix22(x11, x12, x21, x22)

  def unapply(x: BigComplexMatrix22): Option[(BigComplex, BigComplex, BigComplex, BigComplex)] =
    Some((x.x11, x.x12, x.x21, x.x22))

  val zero: BigComplexMatrix22 =
    BigComplexMatrix22(BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.zero)

  val unit: BigComplexMatrix22 =
    BigComplexMatrix22(BigComplex.one, BigComplex.zero, BigComplex.zero, BigComplex.one)

  val pauli1: BigComplexMatrix22 =
    BigComplexMatrix22(BigComplex.zero, BigComplex.one, BigComplex.one, BigComplex.zero)

  val pauli2: BigComplexMatrix22 =
    BigComplexMatrix22(BigComplex.zero, BigComplex.minusI, BigComplex.i, BigComplex.zero)

  val pauli3: BigComplexMatrix22 =
    BigComplexMatrix22(BigComplex.one, BigComplex.zero, BigComplex.zero, BigComplex.minusOne)

  def pauli(x0: BigComplex, x1: BigComplex, x2: BigComplex, x3: BigComplex): BigComplexMatrix22 =
    BigComplexMatrix22(x0 + x3, x1 - BigComplex.i * x2, x1 + BigComplex.i * x2, x0 - x3)

}
