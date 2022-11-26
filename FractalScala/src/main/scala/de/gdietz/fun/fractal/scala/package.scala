package de.gdietz.fun.fractal

import de.gdietz.fun.fractal.scala.util.{Complex, Quaternion, Real, Vector3D}

package object scala {

  type FractalIteratorDefinitionAux[P, X] = FractalIteratorDefinition[P] { type C = X }
  
  type RealFractalIteratorDefinition = FractalIteratorDefinition[Real]
  type ComplexFractalIteratorDefinition = FractalIteratorDefinition[Complex]
  type QuaternionFractalIteratorDefinition = FractalIteratorDefinition[Quaternion]
  type Vector3DFractalIteratorDefinition = FractalIteratorDefinition[Vector3D]

  type RealFractalIteratorDefinitionAux[X] = RealFractalIteratorDefinition { type C = X }
  type ComplexFractalIteratorDefinitionAux[X] = ComplexFractalIteratorDefinition { type C = X }
  type QuaternionFractalIteratorDefinitionAux[X] = QuaternionFractalIteratorDefinition { type C = X }
  type Vector3DFractalIteratorDefinitionAux[X] = Vector3DFractalIteratorDefinition { type C = X }

}
