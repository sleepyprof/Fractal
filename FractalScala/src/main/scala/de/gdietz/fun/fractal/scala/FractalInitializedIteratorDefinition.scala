package de.gdietz.fun.fractal.scala

trait FractalInitializedIteratorDefinition[C] {

  def z0: C

  def zNext(z: C): C

}

object FractalInitializedIteratorDefinition {

  def apply[C](z0Param: C)
              (zNextFunc: C => C): FractalInitializedIteratorDefinition[C] =
    new FractalInitializedIteratorDefinition[C] with Serializable {
      override val z0: C = z0Param
      override def zNext(z: C): C = zNextFunc(z)
    }


  val invalid: FractalInitializedIteratorDefinition[Unit] = InvalidFractalInitializedIteratorDefinition

  protected object InvalidFractalInitializedIteratorDefinition extends FractalInitializedIteratorDefinition[Unit] {
    override val z0: Unit = {}
    override def zNext(z: Unit): Unit = {}
  }

}
