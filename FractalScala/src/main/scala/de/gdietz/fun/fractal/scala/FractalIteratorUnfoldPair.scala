package de.gdietz.fun.fractal.scala

case class FractalIteratorUnfoldPair[X](z0: X, f: X => X)

object FractalIteratorUnfoldPair {

  @inline def unfold[X](z0: X)(f: X => X): FractalIteratorUnfoldPair[X] =
    FractalIteratorUnfoldPair(z0, f)

}
