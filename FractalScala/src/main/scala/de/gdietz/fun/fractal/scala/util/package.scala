package de.gdietz.fun.fractal.scala

package object util {

  type RealVector2 = HigherVector2[OptReal, Real]
  type ComplexVector2 = HigherVector2[OptComplex, Complex]
  type QuaternionVector2 = HigherVector2[OptQuaternion, Quaternion]

}
