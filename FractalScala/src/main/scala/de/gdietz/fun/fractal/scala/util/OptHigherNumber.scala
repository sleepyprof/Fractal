package de.gdietz.fun.fractal.scala.util

trait OptHigherNumber[O <: OptHigherNumber[O, X], X <: O with HigherNumber[X]]
  extends HigherVector[O, O, X] {
  self: O =>

  def isNumber: Boolean

  def foldNumber[Y](ifIsNumber: X => Y)(ifNoNumber: => Y): Y

  @inline final override def const(x: O): O = x
  @inline final override def map(f: O => O): O = f(this)
  @inline final override def mapOp(x: O)(f: (O, O) => O): O = f(this, x)

  @inline final override def forall(cond: O => Boolean): Boolean = cond(this)
  @inline final override def exists(cond: O => Boolean): Boolean = cond(this)

  @inline final override def filter(cond: O => Boolean): O = if (cond(this)) this else none

  @inline final override def isAllNumber: Boolean = isNumber
  @inline final override def existsNumber: Boolean = isNumber


  override def zero: X
  override def unit: X

  @inline final override def +!(x: O): O = this + x
  @inline final override def +!:(x: O): O = x + this
  @inline final override def -!(x: O): O = this - x
  @inline final override def -!:(x: O): O = x - this
  @inline final override def *!(x: O): O = this * x
  @inline final override def *!:(x: O): O = x * this
  @inline final override def /!(x: O): O = this / x
  @inline final override def /!:(x: O): O = x / this


  @inline def +![V <: HigherVector[V, O, X]](x: V): V = this +!: x
  @inline def -![V <: HigherVector[V, O, X]](x: V): V = this -!: x
  @inline def *![V <: HigherVector[V, O, X]](x: V): V = this *!: x
  @inline def /![V <: HigherVector[V, O, X]](x: V): V = this /!: x


  final override def ::(x: O): HigherVector2[O, X] = HigherVector2(x, this)
  final override def toHigherVectorN: HigherVectorN[O, X] = HigherVectorN(this :: Nil)

}
