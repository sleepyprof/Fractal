package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.controller.FractalIteratorManager
import de.gdietz.fun.fractal.formula.FractalIteratorFactory
import de.gdietz.fun.fractal.gui.{AdvancedFractalPreciseIteratorFactorySelector, FractalIteratorFactorySelector}
import de.gdietz.fun.fractal.scala.util.BigComplex
import de.gdietz.fun.fractal.util.{BigCoordinate, Tuple}

abstract class AdvancedFractalScalaPreciseIteratorFactorySelector[T <: Tuple[T], C](listener: FractalIteratorManager[T],
                                                                                    askMaxiter: Boolean = false)
  extends AdvancedFractalPreciseIteratorFactorySelector[T](listener, askMaxiter) {
  selector =>

  def defaultCode: String

  def fractalScalaIteratorFactory(code: String): FractalScalaIteratorFactory[T, C]


  class InteractiveFractalScalaIteratorData(maxiter: Int, description: String)
    extends FractalIteratorFactorySelector.FractalIteratorData[T](null, maxiter, description) {

    private var code = defaultCode

    override def getIteratorFactory: FractalIteratorFactory[T] = {
      val code = AdvancedFractalScalaIteratorFactorySelector.showCodeDialog(selector, this.code)
        .getOrElse(this.code)

      val factory = fractalScalaIteratorFactory(code)
      factory.tryDefinition.left.foreach(e =>
        AdvancedFractalScalaIteratorFactorySelector.showInvalidCodeMessage(selector, e))

      this.code = code

      factory
    }

  }


  override def addScalaIfPossible(maxiter: Int, description: String): Unit = {
    val iteratorData: FractalIteratorFactorySelector.FractalIteratorData[T] =
      new InteractiveFractalScalaIteratorData(maxiter, description)
    addIteratorData(iteratorData)
  }

}


class BigComplexAdvancedFractalScalaPreciseIteratorFactorySelector(listener: FractalIteratorManager[BigCoordinate],
                                                                   askMaxiter: Boolean = false)
  extends AdvancedFractalScalaPreciseIteratorFactorySelector[BigCoordinate, BigComplex](listener, askMaxiter) {
  selector =>

  override def defaultCode: String =
    BigComplexFractalScalaIteratorFactory.defaultCode

  override def fractalScalaIteratorFactory(code: String): BigComplexFractalScalaIteratorFactory =
    BigComplexFractalScalaIteratorFactory(code)

}
