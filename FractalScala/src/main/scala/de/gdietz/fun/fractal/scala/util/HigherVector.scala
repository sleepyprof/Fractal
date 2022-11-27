package de.gdietz.fun.fractal.scala.util

trait HigherVector[V <: HigherVector[V, O, X], O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]]
  extends HigherTuple[V] {
  self: V =>

  def const(x: O): V
  def map(f: O => O): V
  def mapOp(x: V)(f: (O, O) => O): V

  def forall(cond: O => Boolean): Boolean
  def exists(cond: O => Boolean): Boolean

  def filter(cond: O => Boolean): V

  def mapNumber(f: X => O): V
  def mapOpNumber(x: V)(f: (X, X) => O): V

  def filterNumber(cond: X => Boolean): V

  override def isAllNumber: Boolean = forall(_.isNumber)
  override def existsNumber: Boolean = exists(_.isNumber)


  def +!(x: O): V
  def +!:(x: O): V
  def -!(x: O): V
  def -!:(x: O): V

  def *!(x: O): V
  def *!:(x: O): V

  def /!(x: O): V
  def /!:(x: O): V


  def ::(x: O): HigherVector[_, O, X]
  def toHigherVectorN: HigherVectorN[O, X]

}
