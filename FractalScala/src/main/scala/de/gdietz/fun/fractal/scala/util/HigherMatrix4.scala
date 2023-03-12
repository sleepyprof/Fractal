package de.gdietz.fun.fractal.scala.util

case class HigherMatrix4[X <: HigherNumber[X]](x11: X, x12: X, x13: X, x14: X,
                                               x21: X, x22: X, x23: X, x24: X,
                                               x31: X, x32: X, x33: X, x34: X,
                                               x41: X, x42: X, x43: X, x44: X)
  extends HigherMatrix[HigherMatrix4[X], X] {

  override def isZero: Boolean =
    x11.isZero && x12.isZero && x13.isZero && x14.isZero &&
      x21.isZero && x22.isZero && x23.isZero && x24.isZero &&
      x31.isZero && x32.isZero && x33.isZero && x34.isZero &&
      x41.isZero && x42.isZero && x43.isZero && x44.isZero

  override def isUnit: Boolean =
    x11.isUnit && x12.isZero && x13.isZero && x14.isZero &&
      x21.isZero && x22.isUnit && x23.isZero && x24.isZero &&
      x31.isZero && x32.isZero && x33.isUnit && x34.isZero &&
      x41.isZero && x42.isZero && x43.isZero && x44.isUnit

  override def normSqr: Double =
    x11.normSqr + x12.normSqr + x13.normSqr + x14.normSqr +
      x21.normSqr + x22.normSqr + x23.normSqr + x24.normSqr +
      x31.normSqr + x32.normSqr + x33.normSqr + x34.normSqr +
      x41.normSqr + x42.normSqr + x43.normSqr + x44.normSqr


  override def unary_- : HigherMatrix4[X] =
    HigherMatrix4(
      -x11, -x12, -x13, -x14,
      -x21, -x22, -x23, -x24,
      -x31, -x32, -x33, -x34,
      -x41, -x42, -x43, -x44)

  override def +(x: HigherMatrix4[X]): HigherMatrix4[X] =
    HigherMatrix4(
      x11 + x.x11, x12 + x.x12, x13 + x.x13, x14 + x.x14,
      x21 + x.x21, x22 + x.x22, x23 + x.x23, x24 + x.x24,
      x31 + x.x31, x32 + x.x32, x33 + x.x33, x34 + x.x34,
      x41 + x.x41, x42 + x.x42, x43 + x.x43, x44 + x.x44)

  override def -(x: HigherMatrix4[X]): HigherMatrix4[X] =
    HigherMatrix4(
      x11 - x.x11, x12 - x.x12, x13 - x.x13, x14 - x.x14,
      x21 - x.x21, x22 - x.x22, x23 - x.x23, x24 - x.x24,
      x31 - x.x31, x32 - x.x32, x33 - x.x33, x34 - x.x34,
      x41 - x.x41, x42 - x.x42, x43 - x.x43, x44 - x.x44)

  override def *(x: HigherMatrix4[X]): HigherMatrix4[X] =
    HigherMatrix4(
      x11 * x.x11 + x12 * x.x21 + x13 * x.x31 + x14 * x.x41,
      x11 * x.x12 + x12 * x.x22 + x13 * x.x32 + x14 * x.x42,
      x11 * x.x13 + x12 * x.x23 + x13 * x.x33 + x14 * x.x43,
      x11 * x.x14 + x12 * x.x24 + x13 * x.x34 + x14 * x.x44,
      x21 * x.x11 + x22 * x.x21 + x23 * x.x31 + x24 * x.x41,
      x21 * x.x12 + x22 * x.x22 + x23 * x.x32 + x24 * x.x42,
      x21 * x.x13 + x22 * x.x23 + x23 * x.x33 + x24 * x.x43,
      x21 * x.x14 + x22 * x.x24 + x23 * x.x34 + x24 * x.x44,
      x31 * x.x11 + x32 * x.x21 + x33 * x.x31 + x34 * x.x41,
      x31 * x.x12 + x32 * x.x22 + x33 * x.x32 + x34 * x.x42,
      x31 * x.x13 + x32 * x.x23 + x33 * x.x33 + x34 * x.x43,
      x31 * x.x14 + x32 * x.x24 + x33 * x.x34 + x34 * x.x44,
      x41 * x.x11 + x42 * x.x21 + x43 * x.x31 + x44 * x.x41,
      x41 * x.x12 + x42 * x.x22 + x43 * x.x32 + x44 * x.x42,
      x41 * x.x13 + x42 * x.x23 + x43 * x.x33 + x44 * x.x43,
      x41 * x.x14 + x42 * x.x24 + x43 * x.x34 + x44 * x.x44)

  override def trace: X =
    x11 + x22 + x33 + x44

  override def transpose: HigherMatrix4[X] =
    HigherMatrix4(
      x11, x21, x31, x41,
      x12, x22, x32, x42,
      x13, x23, x33, x43,
      x14, x24, x34, x44)

  // TODO too complicated for now (especially for a non-commutative field)
  override def inverse: HigherMatrix4[X] = 
    throw new UnsupportedOperationException("inverse of a 4x4 matrix not supported yet")

  override def conjugate: HigherMatrix4[X] =
    HigherMatrix4(
      x11.conjugate, x12.conjugate, x13.conjugate, x14.conjugate,
      x21.conjugate, x22.conjugate, x23.conjugate, x24.conjugate,
      x31.conjugate, x32.conjugate, x33.conjugate, x34.conjugate,
      x41.conjugate, x42.conjugate, x43.conjugate, x44.conjugate)

  override def zero: HigherMatrix4[X] =
    HigherMatrix4(
      x11.zero, x12.zero, x13.zero, x14.zero,
      x21.zero, x22.zero, x23.zero, x24.zero,
      x31.zero, x32.zero, x33.zero, x34.zero,
      x41.zero, x42.zero, x43.zero, x44.zero)

  override def unit: HigherMatrix4[X] =
    HigherMatrix4(
      x11.unit, x12.zero, x13.zero, x14.zero,
      x21.zero, x22.unit, x23.zero, x24.zero,
      x31.zero, x32.zero, x33.unit, x34.zero,
      x41.zero, x42.zero, x43.zero, x44.unit)

  override def *!(x: X): HigherMatrix4[X] =
    HigherMatrix4(
      x11 * x, x12 * x, x13 * x, x14 * x,
      x21 * x, x22 * x, x23 * x, x24 * x,
      x31 * x, x32 * x, x33 * x, x34 * x,
      x41 * x, x42 * x, x43 * x, x44 * x)

  override def *!:(x: X): HigherMatrix4[X] =
    HigherMatrix4(
      x * x11, x * x12, x * x13, x * x14,
      x * x21, x * x22, x * x23, x * x24,
      x * x31, x * x32, x * x33, x * x34,
      x * x41, x * x42, x * x43, x * x44)

  override def /!(x: X): HigherMatrix4[X] =
    HigherMatrix4(
      x11 / x, x12 / x, x13 / x, x14 / x,
      x21 / x, x22 / x, x23 / x, x24 / x,
      x31 / x, x32 / x, x33 / x, x34 / x,
      x41 / x, x42 / x, x43 / x, x44 / x)

  override def +(r: Double): HigherMatrix4[X] =
    HigherMatrix4(
      x11 + r, x12, x13, x14,
      x21, x22 + r, x23, x24,
      x31, x32, x33 + r, x34,
      x41, x42, x43, x44 + r)

  override def -(r: Double): HigherMatrix4[X] =
    HigherMatrix4(
      x11 - r, x12, x13, x14,
      x21, x22 - r, x23, x24,
      x31, x32, x33 - r, x34,
      x41, x42, x43, x44 - r)

  override def -:(r: Double): HigherMatrix4[X] =
    HigherMatrix4(
      r -: x11, -x12, -x13, -x14,
      -x21, r -: x22, -x23, -x24,
      -x31, -x32, r -: x33, -x34,
      -x41, -x42, -x43, r -: x44)

  override def *(r: Double): HigherMatrix4[X] =
    HigherMatrix4(
      x11 * r, x12 * r, x13 * r, x14 * r,
      x21 * r, x22 * r, x23 * r, x24 * r,
      x31 * r, x32 * r, x33 * r, x34 * r,
      x41 * r, x42 * r, x43 * r, x44 * r)

  override def /(r: Double): HigherMatrix4[X] =
    HigherMatrix4(
      x11 / r, x12 / r, x13 / r, x14 / r,
      x21 / r, x22 / r, x23 / r, x24 / r,
      x31 / r, x32 / r, x33 / r, x34 / r,
      x41 / r, x42 / r, x43 / r, x44 / r)


  override def toString: String = "(" +
      x11 + ", " + x21 + ", " + x31 + ", " + x41 + " | " + 
      x12 + ", " + x22 + ", " + x32 + ", " + x42 + " | " + 
      x13 + ", " + x23 + ", " + x33 + ", " + x43 + " | " + 
      x14 + ", " + x24 + ", " + x34 + ", " + x44 + ")"

}


object RealMatrix4 {

  @inline def apply(x11: Real, x12: Real, x13: Real, x14: Real,
                    x21: Real, x22: Real, x23: Real, x24: Real,
                    x31: Real, x32: Real, x33: Real, x34: Real,
                    x41: Real, x42: Real, x43: Real, x44: Real): RealMatrix4 =
    HigherMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)

  def unapply(x: RealMatrix4): Option[(Real, Real, Real, Real, Real, Real, Real, Real, Real, Real, Real, Real, Real, Real, Real, Real)] =
    Some((
      x.x11, x.x12, x.x13, x.x14,
      x.x21, x.x22, x.x23, x.x24,
      x.x31, x.x32, x.x33, x.x34,
      x.x41, x.x42, x.x43, x.x44))

  val zero: RealMatrix4 =
    RealMatrix4(
      Real.zero, Real.zero, Real.zero, Real.zero,
      Real.zero, Real.zero, Real.zero, Real.zero,
      Real.zero, Real.zero, Real.zero, Real.zero,
      Real.zero, Real.zero, Real.zero, Real.zero)

  val unit: RealMatrix4 =
    RealMatrix4(
      Real.one, Real.zero, Real.zero, Real.zero,
      Real.zero, Real.one, Real.zero, Real.zero,
      Real.zero, Real.zero, Real.one, Real.zero,
      Real.zero, Real.zero, Real.zero, Real.one)

  val dirac0: RealMatrix4 =
    RealMatrix4(
      Real.one, Real.zero, Real.zero, Real.zero,
      Real.zero, Real.one, Real.zero, Real.zero,
      Real.zero, Real.zero, Real.minusOne, Real.zero,
      Real.zero, Real.zero, Real.zero, Real.minusOne)

  val dirac1: RealMatrix4 =
    RealMatrix4(
      Real.zero, Real.zero, Real.zero, Real.one,
      Real.zero, Real.zero, Real.one, Real.zero,
      Real.zero, Real.minusOne, Real.zero, Real.zero,
      Real.minusOne, Real.zero, Real.zero, Real.zero)

  val iDirac2: RealMatrix4 =
    RealMatrix4(
      Real.zero, Real.zero, Real.zero, Real.one,
      Real.zero, Real.zero, Real.minusOne, Real.zero,
      Real.zero, Real.minusOne, Real.zero, Real.zero,
      Real.one, Real.zero, Real.zero, Real.zero)

  val dirac3: RealMatrix4 =
    RealMatrix4(
      Real.zero, Real.zero, Real.one, Real.zero,
      Real.zero, Real.zero, Real.zero, Real.minusOne,
      Real.minusOne, Real.zero, Real.zero, Real.zero,
      Real.zero, Real.one, Real.zero, Real.zero)

}

object ComplexMatrix4 {

  @inline def apply(x11: Complex, x12: Complex, x13: Complex, x14: Complex,
                    x21: Complex, x22: Complex, x23: Complex, x24: Complex,
                    x31: Complex, x32: Complex, x33: Complex, x34: Complex,
                    x41: Complex, x42: Complex, x43: Complex, x44: Complex): ComplexMatrix4 =
    HigherMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)

  def unapply(x: ComplexMatrix4): Option[(Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex, Complex)] =
    Some((
      x.x11, x.x12, x.x13, x.x14,
      x.x21, x.x22, x.x23, x.x24,
      x.x31, x.x32, x.x33, x.x34,
      x.x41, x.x42, x.x43, x.x44))

  val zero: ComplexMatrix4 =
    ComplexMatrix4(
      Complex.zero, Complex.zero, Complex.zero, Complex.zero,
      Complex.zero, Complex.zero, Complex.zero, Complex.zero,
      Complex.zero, Complex.zero, Complex.zero, Complex.zero,
      Complex.zero, Complex.zero, Complex.zero, Complex.zero)

  val unit: ComplexMatrix4 =
    ComplexMatrix4(
      Complex.one, Complex.zero, Complex.zero, Complex.zero,
      Complex.zero, Complex.one, Complex.zero, Complex.zero,
      Complex.zero, Complex.zero, Complex.one, Complex.zero,
      Complex.zero, Complex.zero, Complex.zero, Complex.one)

  val dirac0: ComplexMatrix4 =
    ComplexMatrix4(
      Complex.one, Complex.zero, Complex.zero, Complex.zero,
      Complex.zero, Complex.one, Complex.zero, Complex.zero,
      Complex.zero, Complex.zero, Complex.minusOne, Complex.zero,
      Complex.zero, Complex.zero, Complex.zero, Complex.minusOne)

  val dirac1: ComplexMatrix4 =
    ComplexMatrix4(
      Complex.zero, Complex.zero, Complex.zero, Complex.one,
      Complex.zero, Complex.zero, Complex.one, Complex.zero,
      Complex.zero, Complex.minusOne, Complex.zero, Complex.zero,
      Complex.minusOne, Complex.zero, Complex.zero, Complex.zero)

  val dirac2: ComplexMatrix4 =
    ComplexMatrix4(
      Complex.zero, Complex.zero, Complex.zero, Complex.minusI,
      Complex.zero, Complex.zero, Complex.i, Complex.zero,
      Complex.zero, Complex.i, Complex.zero, Complex.zero,
      Complex.minusI, Complex.zero, Complex.zero, Complex.zero)

  val dirac3: ComplexMatrix4 =
    ComplexMatrix4(
      Complex.zero, Complex.zero, Complex.one, Complex.zero,
      Complex.zero, Complex.zero, Complex.zero, Complex.minusOne,
      Complex.minusOne, Complex.zero, Complex.zero, Complex.zero,
      Complex.zero, Complex.one, Complex.zero, Complex.zero)

  def dirac(x0: Complex, x1: Complex, x2: Complex, x3: Complex): ComplexMatrix4 =
    ComplexMatrix4(
      x0, Complex.zero, x3, x1 - Complex.i * x2,
      Complex.zero, x0, x1 + Complex.i * x2, -x3,
      -x3, -x1 + Complex.i * x2, -x0, Complex.zero,
      -x1 - Complex.i * x2, x3, Complex.zero, -x0)

}

object QuaternionMatrix4 {

  @inline def apply(x11: Quaternion, x12: Quaternion, x13: Quaternion, x14: Quaternion,
                    x21: Quaternion, x22: Quaternion, x23: Quaternion, x24: Quaternion,
                    x31: Quaternion, x32: Quaternion, x33: Quaternion, x34: Quaternion,
                    x41: Quaternion, x42: Quaternion, x43: Quaternion, x44: Quaternion): QuaternionMatrix4 =
    HigherMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)

  def unapply(x: QuaternionMatrix4): Option[(Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion, Quaternion)] =
    Some((
      x.x11, x.x12, x.x13, x.x14,
      x.x21, x.x22, x.x23, x.x24,
      x.x31, x.x32, x.x33, x.x34,
      x.x41, x.x42, x.x43, x.x44))

  val zero: QuaternionMatrix4 =
    QuaternionMatrix4(
      Quaternion.zero, Quaternion.zero, Quaternion.zero, Quaternion.zero,
      Quaternion.zero, Quaternion.zero, Quaternion.zero, Quaternion.zero,
      Quaternion.zero, Quaternion.zero, Quaternion.zero, Quaternion.zero,
      Quaternion.zero, Quaternion.zero, Quaternion.zero, Quaternion.zero)

  val unit: QuaternionMatrix4 =
    QuaternionMatrix4(
      Quaternion.one, Quaternion.zero, Quaternion.zero, Quaternion.zero,
      Quaternion.zero, Quaternion.one, Quaternion.zero, Quaternion.zero,
      Quaternion.zero, Quaternion.zero, Quaternion.one, Quaternion.zero,
      Quaternion.zero, Quaternion.zero, Quaternion.zero, Quaternion.one)

}

object BigRealMatrix4 {

  @inline def apply(x11: BigReal, x12: BigReal, x13: BigReal, x14: BigReal,
                    x21: BigReal, x22: BigReal, x23: BigReal, x24: BigReal,
                    x31: BigReal, x32: BigReal, x33: BigReal, x34: BigReal,
                    x41: BigReal, x42: BigReal, x43: BigReal, x44: BigReal): BigRealMatrix4 =
    HigherMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)

  def unapply(x: BigRealMatrix4): Option[(BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal, BigReal)] =
    Some((
      x.x11, x.x12, x.x13, x.x14,
      x.x21, x.x22, x.x23, x.x24,
      x.x31, x.x32, x.x33, x.x34,
      x.x41, x.x42, x.x43, x.x44))

  val zero: BigRealMatrix4 =
    BigRealMatrix4(
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.zero,
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.zero,
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.zero,
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.zero)

  val unit: BigRealMatrix4 =
    BigRealMatrix4(
      BigReal.one, BigReal.zero, BigReal.zero, BigReal.zero,
      BigReal.zero, BigReal.one, BigReal.zero, BigReal.zero,
      BigReal.zero, BigReal.zero, BigReal.one, BigReal.zero,
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.one)

  val dirac0: BigRealMatrix4 =
    BigRealMatrix4(
      BigReal.one, BigReal.zero, BigReal.zero, BigReal.zero,
      BigReal.zero, BigReal.one, BigReal.zero, BigReal.zero,
      BigReal.zero, BigReal.zero, BigReal.minusOne, BigReal.zero,
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.minusOne)

  val dirac1: BigRealMatrix4 =
    BigRealMatrix4(
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.one,
      BigReal.zero, BigReal.zero, BigReal.one, BigReal.zero,
      BigReal.zero, BigReal.minusOne, BigReal.zero, BigReal.zero,
      BigReal.minusOne, BigReal.zero, BigReal.zero, BigReal.zero)

  val iDirac2: BigRealMatrix4 =
    BigRealMatrix4(
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.one,
      BigReal.zero, BigReal.zero, BigReal.minusOne, BigReal.zero,
      BigReal.zero, BigReal.minusOne, BigReal.zero, BigReal.zero,
      BigReal.one, BigReal.zero, BigReal.zero, BigReal.zero)

  val dirac3: BigRealMatrix4 =
    BigRealMatrix4(
      BigReal.zero, BigReal.zero, BigReal.one, BigReal.zero,
      BigReal.zero, BigReal.zero, BigReal.zero, BigReal.minusOne,
      BigReal.minusOne, BigReal.zero, BigReal.zero, BigReal.zero,
      BigReal.zero, BigReal.one, BigReal.zero, BigReal.zero)

}

object BigComplexMatrix4 {

  @inline def apply(x11: BigComplex, x12: BigComplex, x13: BigComplex, x14: BigComplex,
                    x21: BigComplex, x22: BigComplex, x23: BigComplex, x24: BigComplex,
                    x31: BigComplex, x32: BigComplex, x33: BigComplex, x34: BigComplex,
                    x41: BigComplex, x42: BigComplex, x43: BigComplex, x44: BigComplex): BigComplexMatrix4 =
    HigherMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)

  def unapply(x: BigComplexMatrix4): Option[(BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex, BigComplex)] =
    Some((
      x.x11, x.x12, x.x13, x.x14,
      x.x21, x.x22, x.x23, x.x24,
      x.x31, x.x32, x.x33, x.x34,
      x.x41, x.x42, x.x43, x.x44))

  val zero: BigComplexMatrix4 =
    BigComplexMatrix4(
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.zero,
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.zero,
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.zero,
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.zero)

  val unit: BigComplexMatrix4 =
    BigComplexMatrix4(
      BigComplex.one, BigComplex.zero, BigComplex.zero, BigComplex.zero,
      BigComplex.zero, BigComplex.one, BigComplex.zero, BigComplex.zero,
      BigComplex.zero, BigComplex.zero, BigComplex.one, BigComplex.zero,
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.one)

  val dirac0: BigComplexMatrix4 =
    BigComplexMatrix4(
      BigComplex.one, BigComplex.zero, BigComplex.zero, BigComplex.zero,
      BigComplex.zero, BigComplex.one, BigComplex.zero, BigComplex.zero,
      BigComplex.zero, BigComplex.zero, BigComplex.minusOne, BigComplex.zero,
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.minusOne)

  val dirac1: BigComplexMatrix4 =
    BigComplexMatrix4(
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.one,
      BigComplex.zero, BigComplex.zero, BigComplex.one, BigComplex.zero,
      BigComplex.zero, BigComplex.minusOne, BigComplex.zero, BigComplex.zero,
      BigComplex.minusOne, BigComplex.zero, BigComplex.zero, BigComplex.zero)

  val dirac2: BigComplexMatrix4 =
    BigComplexMatrix4(
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.minusI,
      BigComplex.zero, BigComplex.zero, BigComplex.i, BigComplex.zero,
      BigComplex.zero, BigComplex.i, BigComplex.zero, BigComplex.zero,
      BigComplex.minusI, BigComplex.zero, BigComplex.zero, BigComplex.zero)

  val dirac3: BigComplexMatrix4 =
    BigComplexMatrix4(
      BigComplex.zero, BigComplex.zero, BigComplex.one, BigComplex.zero,
      BigComplex.zero, BigComplex.zero, BigComplex.zero, BigComplex.minusOne,
      BigComplex.minusOne, BigComplex.zero, BigComplex.zero, BigComplex.zero,
      BigComplex.zero, BigComplex.one, BigComplex.zero, BigComplex.zero)

  def dirac(x0: BigComplex, x1: BigComplex, x2: BigComplex, x3: BigComplex): BigComplexMatrix4 =
    BigComplexMatrix4(
      x0, BigComplex.zero, x3, x1 - BigComplex.i * x2,
      BigComplex.zero, x0, x1 + BigComplex.i * x2, -x3,
      -x3, -x1 + BigComplex.i * x2, -x0, BigComplex.zero,
      -x1 - BigComplex.i * x2, x3, BigComplex.zero, -x0)

}
