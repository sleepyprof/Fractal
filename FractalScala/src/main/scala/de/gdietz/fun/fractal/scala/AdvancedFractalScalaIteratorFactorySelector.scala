package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.controller.FractalIteratorManager
import de.gdietz.fun.fractal.formula.FractalIteratorFactory
import de.gdietz.fun.fractal.gui.AdvancedFractalIteratorFactorySelector
import de.gdietz.fun.fractal.gui.FractalIteratorFactorySelector.FractalIteratorData
import de.gdietz.fun.fractal.scala.util.{Complex, Quaternion, Vector3D}
import de.gdietz.fun.fractal.util.{Coordinate, Coordinate3D, Coordinate4D, Tuple}

import javax.swing.{JOptionPane, JScrollPane, JTextArea}

abstract class AdvancedFractalScalaIteratorFactorySelector[T <: Tuple[T], C](listener: FractalIteratorManager[T],
                                                                             askMaxiter: Boolean = false)
  extends AdvancedFractalIteratorFactorySelector[T](listener, askMaxiter) {
  selector =>

  def defaultCode: String

  def fractalScalaIteratorFactory(code: String): FractalScalaIteratorFactory[T, C]


  class InteractiveFractalScalaIteratorData(maxiter: Int, description: String)
    extends FractalIteratorData[T](null, maxiter, description) {

    private var code = defaultCode

    override def getIteratorFactory: FractalIteratorFactory[T] = {
      val textCode: JTextArea = new JTextArea(12, 80)
      val scrollTextCode: JScrollPane = new JScrollPane(textCode)
      textCode.setText(this.code)

      val response = JOptionPane.showOptionDialog(selector, scrollTextCode, "Please enter code",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null)

      val code0 = if (response == JOptionPane.OK_OPTION) textCode.getText else null
      val code = Option(code0).getOrElse(this.code)

      val factory = fractalScalaIteratorFactory(code)
      factory.tryDefinition.left.foreach { e =>
        val message = "Invalid Scala code: " + e.getMessage
        JOptionPane.showMessageDialog(selector, message, "Error", JOptionPane.ERROR_MESSAGE)
      }

      this.code = code

      factory
    }

  }


  override def addScalaIfPossible(maxiter: Int, description: String): Unit = {
    val iteratorData: FractalIteratorData[T] = new InteractiveFractalScalaIteratorData(maxiter, description)
    addIteratorData(iteratorData)
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
