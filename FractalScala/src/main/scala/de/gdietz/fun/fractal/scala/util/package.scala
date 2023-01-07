package de.gdietz.fun.fractal.scala

package object util {

  type RealVector[V <: RealVector[V]] = HigherVector[V, OptReal, Real]
  type ComplexVector[V <: ComplexVector[V]] = HigherVector[V, OptComplex, Complex]
  type QuaternionVector[V <: QuaternionVector[V]] = HigherVector[V, OptQuaternion, Quaternion]
  type BigComplexVector[V <: BigComplexVector[V]] = HigherVector[V, OptBigComplex, BigComplex]
  
  type RealVector2 = HigherVector2[OptReal, Real]
  type ComplexVector2 = HigherVector2[OptComplex, Complex]
  type QuaternionVector2 = HigherVector2[OptQuaternion, Quaternion]
  type BigComplexVector2 = HigherVector2[OptBigComplex, BigComplex]

  type RealVector3 = HigherVector3[OptReal, Real]
  type ComplexVector3 = HigherVector3[OptComplex, Complex]
  type QuaternionVector3 = HigherVector3[OptQuaternion, Quaternion]
  type BigComplexVector3 = HigherVector3[OptBigComplex, BigComplex]

  type RealVector4 = HigherVector4[OptReal, Real]
  type ComplexVector4 = HigherVector4[OptComplex, Complex]
  type QuaternionVector4 = HigherVector4[OptQuaternion, Quaternion]
  type BigComplexVector4 = HigherVector4[OptBigComplex, BigComplex]

  type RealVectorN = HigherVectorN[OptReal, Real]
  type ComplexVectorN = HigherVectorN[OptComplex, Complex]
  type QuaternionVectorN = HigherVectorN[OptQuaternion, Quaternion]
  type BigComplexVectorN = HigherVectorN[OptBigComplex, BigComplex]

}
