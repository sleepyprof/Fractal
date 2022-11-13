package de.gdietz.fun.fractal.scala

package object util {

  type RealVector2 = HigherVector2[OptReal, Real]
  type ComplexVector2 = HigherVector2[OptComplex, Complex]
  type QuaternionVector2 = HigherVector2[OptQuaternion, Quaternion]

  type RealVector3 = HigherVector3[OptReal, Real]
  type ComplexVector3 = HigherVector3[OptComplex, Complex]
  type QuaternionVector3 = HigherVector3[OptQuaternion, Quaternion]

  type RealVector4 = HigherVector4[OptReal, Real]
  type ComplexVector4 = HigherVector4[OptComplex, Complex]
  type QuaternionVector4 = HigherVector4[OptQuaternion, Quaternion]

  type RealVectorN = HigherVectorN[OptReal, Real]
  type ComplexVectorN = HigherVectorN[OptComplex, Complex]
  type QuaternionVectorN = HigherVectorN[OptQuaternion, Quaternion]

}
