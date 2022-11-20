package de.gdietz.fun.fractal

import de.gdietz.fun.fractal.scala.util.{Complex, Quaternion, Real}

package object scala {

  type FractalIteratorDefinitionAux[P, X] = FractalIteratorDefinition[P] { type C = X }
  
  type RealFractalIteratorDefinition = FractalIteratorDefinition[Real]
  type ComplexFractalIteratorDefinition = FractalIteratorDefinition[Complex]
  type QuaternionFractalIteratorDefinition = FractalIteratorDefinition[Quaternion]

  type RealFractalIteratorDefinitionAux[X] = RealFractalIteratorDefinition { type C = X }
  type ComplexFractalIteratorDefinitionAux[X] = ComplexFractalIteratorDefinition { type C = X }
  type QuaternionFractalIteratorDefinitionAux[X] = QuaternionFractalIteratorDefinition { type C = X }

}
