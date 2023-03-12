package de.gdietz.fun.fractal.scala.util

trait HigherMatrix[M <: HigherMatrix[M, X], X <: HigherNumber[X]]
  extends HigherNumber[M] {
  self: M =>

  def trace: X

  def transpose: M

  def *!(x: X): M
  def *!:(x: X): M

  def /!(x: X): M
  @inline def /!:(x: X): M = x *!: inverse

  override def /:(r: Double): M = r *: inverse

  @inline override def sqr: M = this * this
  @inline override def cube: M = this * this * this

}


object RealMatrix {

  @inline def apply(x11: Real, x12: Real, x21: Real, x22: Real): RealMatrix[RealMatrix2] =
    RealMatrix2(x11, x12, x21, x22)

  @inline def apply(x11: Real, x12: Real, x13: Real, x14: Real,
                    x21: Real, x22: Real, x23: Real, x24: Real,
                    x31: Real, x32: Real, x33: Real, x34: Real,
                    x41: Real, x42: Real, x43: Real, x44: Real): RealMatrix[RealMatrix4] =
    RealMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)
  
  @inline def pauli1: RealMatrix[RealMatrix2] =
    RealMatrix2.pauli1

  @inline def iPauli2: RealMatrix[RealMatrix2] =
    RealMatrix2.iPauli2

  @inline def pauli3: RealMatrix[RealMatrix2] =
    RealMatrix2.pauli3

  @inline def dirac1: RealMatrix[RealMatrix4] =
    RealMatrix4.dirac1

  @inline def iDirac2: RealMatrix[RealMatrix4] =
    RealMatrix4.iDirac2

  @inline def dirac3: RealMatrix[RealMatrix4] =
    RealMatrix4.dirac3

}

object ComplexMatrix {

  @inline def apply(x11: Complex, x12: Complex, x21: Complex, x22: Complex): ComplexMatrix[ComplexMatrix2] =
    ComplexMatrix2(x11, x12, x21, x22)

  @inline def apply(x11: Complex, x12: Complex, x13: Complex, x14: Complex,
                    x21: Complex, x22: Complex, x23: Complex, x24: Complex,
                    x31: Complex, x32: Complex, x33: Complex, x34: Complex,
                    x41: Complex, x42: Complex, x43: Complex, x44: Complex): ComplexMatrix[ComplexMatrix4] =
    ComplexMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)

  @inline def pauli1: ComplexMatrix[ComplexMatrix2] =
    ComplexMatrix2.pauli1

  @inline def pauli2: ComplexMatrix[ComplexMatrix2] =
    ComplexMatrix2.pauli2

  @inline def pauli3: ComplexMatrix[ComplexMatrix2] =
    ComplexMatrix2.pauli3

  @inline def pauli(x0: Complex, x1: Complex, x2: Complex, x3: Complex): ComplexMatrix[ComplexMatrix2] =
    ComplexMatrix2.pauli(x0, x1, x2, x3)

  @inline def dirac1: ComplexMatrix[ComplexMatrix4] =
    ComplexMatrix4.dirac1

  @inline def dirac2: ComplexMatrix[ComplexMatrix4] =
    ComplexMatrix4.dirac2

  @inline def dirac3: ComplexMatrix[ComplexMatrix4] =
    ComplexMatrix4.dirac3

  @inline def dirac(x0: Complex, x1: Complex, x2: Complex, x3: Complex): ComplexMatrix[ComplexMatrix4] =
    ComplexMatrix4.dirac(x0, x1, x2, x3)

}

object QuaternionMatrix {

  @inline def apply(x11: Quaternion, x12: Quaternion, x21: Quaternion, x22: Quaternion): QuaternionMatrix[QuaternionMatrix2] =
    QuaternionMatrix2(x11, x12, x21, x22)

  @inline def apply(x11: Quaternion, x12: Quaternion, x13: Quaternion, x14: Quaternion,
                    x21: Quaternion, x22: Quaternion, x23: Quaternion, x24: Quaternion,
                    x31: Quaternion, x32: Quaternion, x33: Quaternion, x34: Quaternion,
                    x41: Quaternion, x42: Quaternion, x43: Quaternion, x44: Quaternion): QuaternionMatrix[QuaternionMatrix4] =
    QuaternionMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)
  
}

object BigRealMatrix {

  @inline def apply(x11: BigReal, x12: BigReal, x21: BigReal, x22: BigReal): BigRealMatrix[BigRealMatrix2] =
    BigRealMatrix2(x11, x12, x21, x22)

  @inline def apply(x11: BigReal, x12: BigReal, x13: BigReal, x14: BigReal,
                    x21: BigReal, x22: BigReal, x23: BigReal, x24: BigReal,
                    x31: BigReal, x32: BigReal, x33: BigReal, x34: BigReal,
                    x41: BigReal, x42: BigReal, x43: BigReal, x44: BigReal): BigRealMatrix[BigRealMatrix4] =
    BigRealMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)

  @inline def pauli1: BigRealMatrix[BigRealMatrix2] =
    BigRealMatrix2.pauli1

  @inline def iPauli2: BigRealMatrix[BigRealMatrix2] =
    BigRealMatrix2.iPauli2

  @inline def pauli3: BigRealMatrix[BigRealMatrix2] =
    BigRealMatrix2.pauli3

  @inline def dirac1: BigRealMatrix[BigRealMatrix4] =
    BigRealMatrix4.dirac1

  @inline def iDirac2: BigRealMatrix[BigRealMatrix4] =
    BigRealMatrix4.iDirac2

  @inline def dirac3: BigRealMatrix[BigRealMatrix4] =
    BigRealMatrix4.dirac3

}

object BigComplexMatrix {

  @inline def apply(x11: BigComplex, x12: BigComplex, x21: BigComplex, x22: BigComplex): BigComplexMatrix[BigComplexMatrix2] =
    BigComplexMatrix2(x11, x12, x21, x22)

  @inline def apply(x11: BigComplex, x12: BigComplex, x13: BigComplex, x14: BigComplex,
                    x21: BigComplex, x22: BigComplex, x23: BigComplex, x24: BigComplex,
                    x31: BigComplex, x32: BigComplex, x33: BigComplex, x34: BigComplex,
                    x41: BigComplex, x42: BigComplex, x43: BigComplex, x44: BigComplex): BigComplexMatrix[BigComplexMatrix4] =
    BigComplexMatrix4(
      x11, x12, x13, x14,
      x21, x22, x23, x24,
      x31, x32, x33, x34,
      x41, x42, x43, x44)
  
  @inline def pauli1: BigComplexMatrix[BigComplexMatrix2] =
    BigComplexMatrix2.pauli1

  @inline def pauli2: BigComplexMatrix[BigComplexMatrix2] =
    BigComplexMatrix2.pauli2

  @inline def pauli3: BigComplexMatrix[BigComplexMatrix2] =
    BigComplexMatrix2.pauli3

  @inline def pauli(x0: BigComplex, x1: BigComplex, x2: BigComplex, x3: BigComplex): BigComplexMatrix[BigComplexMatrix2] =
    BigComplexMatrix2.pauli(x0, x1, x2, x3)

  @inline def dirac1: BigComplexMatrix[BigComplexMatrix4] =
    BigComplexMatrix4.dirac1

  @inline def dirac2: BigComplexMatrix[BigComplexMatrix4] =
    BigComplexMatrix4.dirac2

  @inline def dirac3: BigComplexMatrix[BigComplexMatrix4] =
    BigComplexMatrix4.dirac3

  @inline def dirac(x0: BigComplex, x1: BigComplex, x2: BigComplex, x3: BigComplex): BigComplexMatrix[BigComplexMatrix4] =
    BigComplexMatrix4.dirac(x0, x1, x2, x3)

}
