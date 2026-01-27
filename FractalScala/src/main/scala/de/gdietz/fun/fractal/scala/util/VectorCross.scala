package de.gdietz.fun.fractal.scala.util

trait VectorCross[X <: VectorCross[X]]
  extends Vector[X] {
  self: X =>

  def *#(x: X): X

}
