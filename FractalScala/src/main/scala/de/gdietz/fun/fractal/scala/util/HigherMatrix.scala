package de.gdietz.fun.fractal.scala.util

trait HigherMatrix[M <: HigherMatrix[M, X], X <: HigherNumber[X]]
  extends HigherRealModNumber[M] with NormedNumber {
  self: M =>

  @inline def unary_+ : M = this
  def unary_- : M

  def +(x: M): M
  def -(x: M): M

  def *(x: M): M

  def trace: X

  def inverse: M

  def /(x: M): M = this * x.inverse
  def \(x: M): M = x.inverse * this

  def conjugate: M

  def zero: M
  def unit: M

  def *!(x: X): M
  def *!:(x: X): M

  def /!(x: X): M
  @inline def /!:(x: X): M = x *!: inverse

  override def /:(r: Double): M = r *: inverse

  @inline def sqr: M = this * this
  @inline def cube: M = this * this * this

  def pow(n: Int): M = n match {
    case 2 => sqr
    case 3 => cube
    case -1 => inverse
    case 1 => this
    case 0 => unit
    case n if n < 0 => inverse.pow(-n)
    case n if n % 3 == 0 => cube.pow(n / 3)
    case n if n % 2 == 0 => sqr.pow(n / 2)
    case _ => this * sqr.pow((n - 1) / 2)
  }

  @inline def **(n: Int): M = pow(n)

}


object RealMatrix {

  @inline def apply(x11: Real, x12: Real, x21: Real, x22: Real): RealMatrix[RealMatrix22] =
    RealMatrix22(x11, x12, x21, x22)

  @inline def pauli1: RealMatrix[RealMatrix22] =
    RealMatrix22.pauli1

  @inline def iPauli2: RealMatrix[RealMatrix22] =
    RealMatrix22.iPauli2

  @inline def pauli3: RealMatrix[RealMatrix22] =
    RealMatrix22.pauli3

}

object ComplexMatrix {

  @inline def apply(x11: Complex, x12: Complex, x21: Complex, x22: Complex): ComplexMatrix[ComplexMatrix22] =
    ComplexMatrix22(x11, x12, x21, x22)

  @inline def pauli1: ComplexMatrix[ComplexMatrix22] =
    ComplexMatrix22.pauli1

  @inline def pauli2: ComplexMatrix[ComplexMatrix22] =
    ComplexMatrix22.pauli2

  @inline def pauli3: ComplexMatrix[ComplexMatrix22] =
    ComplexMatrix22.pauli3

  @inline def pauli(x0: Complex, x1: Complex, x2: Complex, x3: Complex): ComplexMatrix[ComplexMatrix22] =
    ComplexMatrix22.pauli(x0, x1, x2, x3)

}

object QuaternionMatrix {

  @inline def apply(x11: Quaternion, x12: Quaternion, x21: Quaternion, x22: Quaternion): QuaternionMatrix[QuaternionMatrix22] =
    QuaternionMatrix22(x11, x12, x21, x22)

}

object BigRealMatrix {

  @inline def apply(x11: BigReal, x12: BigReal, x21: BigReal, x22: BigReal): BigRealMatrix[BigRealMatrix22] =
    BigRealMatrix22(x11, x12, x21, x22)

  @inline def pauli1: BigRealMatrix[BigRealMatrix22] =
    BigRealMatrix22.pauli1

  @inline def iPauli2: BigRealMatrix[BigRealMatrix22] =
    BigRealMatrix22.iPauli2

  @inline def pauli3: BigRealMatrix[BigRealMatrix22] =
    BigRealMatrix22.pauli3

}

object BigComplexMatrix {

  @inline def apply(x11: BigComplex, x12: BigComplex, x21: BigComplex, x22: BigComplex): BigComplexMatrix[BigComplexMatrix22] =
    BigComplexMatrix22(x11, x12, x21, x22)

  @inline def pauli1: BigComplexMatrix[BigComplexMatrix22] =
    BigComplexMatrix22.pauli1

  @inline def pauli2: BigComplexMatrix[BigComplexMatrix22] =
    BigComplexMatrix22.pauli2

  @inline def pauli3: BigComplexMatrix[BigComplexMatrix22] =
    BigComplexMatrix22.pauli3

  @inline def pauli(x0: BigComplex, x1: BigComplex, x2: BigComplex, x3: BigComplex): BigComplexMatrix[BigComplexMatrix22] =
    BigComplexMatrix22.pauli(x0, x1, x2, x3)

}
