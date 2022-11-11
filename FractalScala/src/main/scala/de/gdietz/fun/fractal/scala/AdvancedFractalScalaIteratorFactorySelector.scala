package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.controller.FractalIteratorManager
import de.gdietz.fun.fractal.formula.FractalIteratorFactory
import de.gdietz.fun.fractal.gui.AdvancedFractalIteratorFactorySelector
import de.gdietz.fun.fractal.gui.FractalIteratorFactorySelector.FractalIteratorData
import de.gdietz.fun.fractal.util.Coordinate

import javax.swing.{JOptionPane, JScrollPane, JTextArea}

class AdvancedFractalScalaIteratorFactorySelector(listener: FractalIteratorManager[Coordinate],
                                                  askMaxiter: Boolean = false)
  extends AdvancedFractalIteratorFactorySelector[Coordinate](listener, askMaxiter) {
  selector =>

  class InteractiveFractalScalaIteratorData(maxiter: Int, description: String)
    extends FractalIteratorData[Coordinate](null, maxiter, description) {

    private var code = FractalScalaIteratorFactory.z3z2Code

    override def getIteratorFactory: FractalIteratorFactory[Coordinate] = {
      val textCode: JTextArea = new JTextArea(5, 50)
      val scrollTextCode: JScrollPane = new JScrollPane(textCode)
      textCode.setText(this.code)

      val response = JOptionPane.showOptionDialog(selector, scrollTextCode, "Please enter code",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null)

      val code0 = if (response == JOptionPane.OK_OPTION) textCode.getText else null
      val code = Option(code0).getOrElse(this.code)

      val factory = new FractalScalaIteratorFactory(code)
      factory.tryDefinition.left.foreach { e =>
        val message = "Invalid Scala code: " + e.getMessage
        JOptionPane.showMessageDialog(selector, message, "Error", JOptionPane.ERROR_MESSAGE)
      }

      this.code = code

      factory
    }

  }


  override def addScalaIfPossible(maxiter: Int, description: String): Unit = {
    val iteratorData: FractalIteratorData[Coordinate] = new InteractiveFractalScalaIteratorData(maxiter, description)
    addIteratorData(iteratorData)
  }

}
