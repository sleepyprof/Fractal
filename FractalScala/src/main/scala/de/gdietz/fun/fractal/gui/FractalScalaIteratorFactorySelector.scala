package de.gdietz.fun.fractal.gui

import de.gdietz.fun.fractal.controller.FractalIteratorManager
import de.gdietz.fun.fractal.scala.FractalScalaIteratorFactory
import de.gdietz.fun.fractal.util.Coordinate
import de.gdietz.gui.swing.JNumberCachedTextField

import java.awt.{Dimension, GridBagConstraints, GridBagLayout}
import java.awt.event.{ActionEvent, ActionListener}
import java.text.NumberFormat
import javax.swing.{JButton, JLabel, JPanel, JScrollPane, JTextArea}

class FractalScalaIteratorFactorySelector(initialIteratorFactory: FractalScalaIteratorFactory,
                                          initialMaxiter: Int,
                                          listener: FractalIteratorManager[Coordinate],
                                          askMaxiter: Boolean)
  extends JPanel(new GridBagLayout)
    with ActionListener {

  def createNumberTextField(): JNumberCachedTextField = {
    val format: NumberFormat = NumberFormat.getInstance
    format.setParseIntegerOnly(true)

    val text: JNumberCachedTextField = new JNumberCachedTextField(format)
    text.setPreferredSize(new Dimension(60, text.getPreferredSize.height))
    text
  }

  private val labelCode: JLabel = new JLabel("Scala code:")

  private val textCode: JTextArea = new JTextArea(5, 50)
  private val scrollTextCode: JScrollPane = new JScrollPane(textCode)
  textCode.setText(initialIteratorFactory.code)

  private val labelMaxiter = new JLabel("iter:")

  private val textMaxiter = createNumberTextField()
  textMaxiter.setValue(initialMaxiter)

  private val setButton: JButton = new JButton("Set")
  setButton.addActionListener(this)

  def createGUI(): Unit = {
    val c = new GridBagConstraints
    c.weighty = 1.0
    c.weightx = 1.0
    c.gridwidth = 1
    c.fill = GridBagConstraints.NONE
    add(labelCode, c)
    c.anchor = GridBagConstraints.WEST
    c.gridwidth = GridBagConstraints.REMAINDER
    add(scrollTextCode, c)

    if (askMaxiter) {
      c.gridwidth = 1
      c.anchor = GridBagConstraints.CENTER
      c.fill = GridBagConstraints.NONE
      add(labelMaxiter, c)
      c.anchor = GridBagConstraints.WEST
      c.gridwidth = GridBagConstraints.REMAINDER
      add(textMaxiter, c)
    }

    c.weightx = 0.0
    c.weighty = 0.0
    c.anchor = GridBagConstraints.CENTER
    c.fill = GridBagConstraints.NONE
    add(setButton, c)
  }

  createGUI()

  override def actionPerformed(event: ActionEvent): Unit = {
    val code = textCode.getText
    val maxiter = textMaxiter.getIntValueCommit
    if (event.getSource eq setButton) {
      val iteratorFactory = FractalScalaIteratorFactory(code)
      listener.setIteratorData(iteratorFactory, maxiter)
    }
  }

}
