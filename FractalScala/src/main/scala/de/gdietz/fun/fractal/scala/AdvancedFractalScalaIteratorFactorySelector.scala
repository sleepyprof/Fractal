package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.controller.FractalIteratorManager
import de.gdietz.fun.fractal.formula.FractalIteratorFactory
import de.gdietz.fun.fractal.gui.{AdvancedFractalIteratorFactorySelector, FractalIteratorFactorySelector}
import de.gdietz.fun.fractal.scala.util.{BigComplex, Complex, Quaternion, Vector3D}
import de.gdietz.fun.fractal.util.{BigCoordinate, Coordinate, Coordinate3D, Coordinate4D, Tuple}

import java.awt.Component
import javax.swing.{JOptionPane, JScrollPane, JTextArea}

abstract class AdvancedFractalScalaIteratorFactorySelector[T <: Tuple[T], C](listener: FractalIteratorManager[T],
                                                                             askMaxiter: Boolean = false)
  extends AdvancedFractalIteratorFactorySelector[T](listener, askMaxiter) {
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

object AdvancedFractalScalaIteratorFactorySelector {

  def showCodeDialog(parentComponent: Component, code: String): Option[String] = {
    val textCode: JTextArea = new JTextArea(12, 80)
    val scrollTextCode: JScrollPane = new JScrollPane(textCode)
    textCode.setText(code)

    val response = JOptionPane.showOptionDialog(parentComponent, scrollTextCode, "Please enter code",
      JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null)

    val code0 = if (response == JOptionPane.OK_OPTION) textCode.getText else null
    Option(code0)
  }

  def showInvalidCodeMessage(parentComponent: Component,
                             error: Throwable): Unit = {
    val message = "Invalid Scala code: " + error.getMessage
    JOptionPane.showMessageDialog(parentComponent, message, "Error", JOptionPane.ERROR_MESSAGE)
  }

}


class ComplexAdvancedFractalScalaIteratorFactorySelector(listener: FractalIteratorManager[Coordinate],
                                                         askMaxiter: Boolean = false)
  extends AdvancedFractalScalaIteratorFactorySelector[Coordinate, Complex](listener, askMaxiter) {
  selector =>

  override def defaultCode: String =
    ComplexFractalScalaIteratorFactory.defaultCode

  override def fractalScalaIteratorFactory(code: String): ComplexFractalScalaIteratorFactory =
    ComplexFractalScalaIteratorFactory(code)

}

class QuaternionAdvancedFractalScalaIteratorFactorySelector(listener: FractalIteratorManager[Coordinate4D],
                                                            askMaxiter: Boolean = false)
  extends AdvancedFractalScalaIteratorFactorySelector[Coordinate4D, Quaternion](listener, askMaxiter) {
  selector =>

  override def defaultCode: String =
    QuaternionFractalScalaIteratorFactory.defaultCode

  override def fractalScalaIteratorFactory(code: String): QuaternionFractalScalaIteratorFactory =
    QuaternionFractalScalaIteratorFactory(code)

}

class Vector3DAdvancedFractalScalaIteratorFactorySelector(listener: FractalIteratorManager[Coordinate3D],
                                                          askMaxiter: Boolean = false)
  extends AdvancedFractalScalaIteratorFactorySelector[Coordinate3D, Vector3D](listener, askMaxiter) {
  selector =>

  override def defaultCode: String =
    Vector3DFractalScalaIteratorFactory.defaultCode

  override def fractalScalaIteratorFactory(code: String): Vector3DFractalScalaIteratorFactory =
    Vector3DFractalScalaIteratorFactory(code)

}

class BigComplexAdvancedFractalScalaIteratorFactorySelector(listener: FractalIteratorManager[BigCoordinate],
                                                            askMaxiter: Boolean = false)
  extends AdvancedFractalScalaIteratorFactorySelector[BigCoordinate, BigComplex](listener, askMaxiter) {
  selector =>

  override def defaultCode: String =
    BigComplexFractalScalaIteratorFactory.defaultCode

  override def fractalScalaIteratorFactory(code: String): BigComplexFractalScalaIteratorFactory =
    BigComplexFractalScalaIteratorFactory(code)

}
