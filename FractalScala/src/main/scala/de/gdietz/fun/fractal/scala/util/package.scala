package de.gdietz.fun.fractal.scala

package object util {

  type RealVector[V <: RealVector[V]] = HigherVector[V, OptReal, Real]
  type ComplexVector[V <: ComplexVector[V]] = HigherVector[V, OptComplex, Complex]
  type QuaternionVector[V <: QuaternionVector[V]] = HigherVector[V, OptQuaternion, Quaternion]
  type BigRealVector[V <: BigRealVector[V]] = HigherVector[V, OptBigReal, BigReal]
  type BigComplexVector[V <: BigComplexVector[V]] = HigherVector[V, OptBigComplex, BigComplex]
  
  type RealVector2 = HigherVector2[OptReal, Real]
  type ComplexVector2 = HigherVector2[OptComplex, Complex]
  type QuaternionVector2 = HigherVector2[OptQuaternion, Quaternion]
  type BigRealVector2 = HigherVector2[OptBigReal, BigReal]
  type BigComplexVector2 = HigherVector2[OptBigComplex, BigComplex]

  type RealVector3 = HigherVector3[OptReal, Real]
  type ComplexVector3 = HigherVector3[OptComplex, Complex]
  type QuaternionVector3 = HigherVector3[OptQuaternion, Quaternion]
  type BigRealVector3 = HigherVector3[OptBigReal, BigReal]
  type BigComplexVector3 = HigherVector3[OptBigComplex, BigComplex]

  type RealVector4 = HigherVector4[OptReal, Real]
  type ComplexVector4 = HigherVector4[OptComplex, Complex]
  type QuaternionVector4 = HigherVector4[OptQuaternion, Quaternion]
  type BigRealVector4 = HigherVector4[OptBigReal, BigReal]
  type BigComplexVector4 = HigherVector4[OptBigComplex, BigComplex]

  type RealVectorN = HigherVectorN[OptReal, Real]
  type ComplexVectorN = HigherVectorN[OptComplex, Complex]
  type QuaternionVectorN = HigherVectorN[OptQuaternion, Quaternion]
  type BigRealVectorN = HigherVectorN[OptBigReal, BigReal]
  type BigComplexVectorN = HigherVectorN[OptBigComplex, BigComplex]

  type RealMatrix[M <: RealMatrix[M]] = HigherMatrix[M, Real]
  type ComplexMatrix[M <: ComplexMatrix[M]] = HigherMatrix[M, Complex]
  type QuaternionMatrix[M <: QuaternionMatrix[M]] = HigherMatrix[M, Quaternion]
  type BigRealMatrix[M <: BigRealMatrix[M]] = HigherMatrix[M, BigReal]
  type BigComplexMatrix[M <: BigComplexMatrix[M]] = HigherMatrix[M, BigComplex]

  type RealMatrix2 = HigherMatrix2[Real]
  type ComplexMatrix2 = HigherMatrix2[Complex]
  type QuaternionMatrix2 = HigherMatrix2[Quaternion]
  type BigRealMatrix2 = HigherMatrix2[BigReal]
  type BigComplexMatrix2 = HigherMatrix2[BigComplex]

  type RealMatrix4 = HigherMatrix4[Real]
  type ComplexMatrix4 = HigherMatrix4[Complex]
  type QuaternionMatrix4 = HigherMatrix4[Quaternion]
  type BigRealMatrix4 = HigherMatrix4[BigReal]
  type BigComplexMatrix4 = HigherMatrix4[BigComplex]

}
