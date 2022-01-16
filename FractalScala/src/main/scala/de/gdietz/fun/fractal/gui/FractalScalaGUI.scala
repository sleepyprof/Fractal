/*
 * Projekt: Detact
 *
 * Copyright(c) Symate GmbH. Unerlaubtes Kopieren und Nutzen untersagt.
 */
package de.gdietz.fun.fractal.gui

import de.gdietz.fun.fractal.controller.{Fractal2DIterateController, Fractal2DPairedController, Fractal2DPairedControllerImpl, FractalIteratorManager, FractalSimpleController, WatchingController}
import de.gdietz.fun.fractal.formula.FractalIteratorFactory
import de.gdietz.fun.fractal.model.{FractalEndlessIterateModel, FractalIterateModel, FractalSimpleIterateModel}
import de.gdietz.fun.fractal.palette.Palette
import de.gdietz.fun.fractal.scala.FractalScalaIteratorFactory
import de.gdietz.fun.fractal.util.Coordinate
import de.gdietz.fun.fractal.view.{ColorStrategy, FractalEditView, FractalPicture, FractalPictureImpl, FractalPictureView, FractalPictureViewBase, FractalScaledPictureView, PaletteView, SimpleColorStrategy}

import java.awt.{GridBagConstraints, GridBagLayout}
import javax.swing.{JComponent, JPanel}

class FractalScalaGUI(maxiter: Int,
                      width: Int, height: Int,
                      from: Coordinate, to: Coordinate,
                      endless: Boolean, resizeable: Boolean)
  extends JPanel(new GridBagLayout)
    with PaletteView
    with FractalIteratorManager[Coordinate] {

  private val iteratorFactory: FractalScalaIteratorFactory =
    FractalScalaIteratorFactory.default

  private val model: FractalIterateModel[Integer] =
    if (endless)
      new FractalEndlessIterateModel(width, height, from, to, iteratorFactory, maxiter, false)
    else
      new FractalSimpleIterateModel(width, height, from, to, iteratorFactory, maxiter, false)

  private val controller: Fractal2DIterateController = new FractalSimpleController(model)
  private val pairedController: Fractal2DPairedController[Coordinate] = new Fractal2DPairedControllerImpl(controller)

  //noinspection ScalaUnusedSymbol
  private val watcher: WatchingController = new WatchingController(controller)

  private val paletteSelector: PaletteSelector = new PaletteSelector(this)
  private var palette: Palette = paletteSelector.getPalette

  private val coloring: ColorStrategy[Integer] = new SimpleColorStrategy(palette, false)

  private val picture: FractalPicture[Coordinate] = new FractalPictureImpl[Integer](model, coloring)

  private val iteratorSelector = new FractalScalaIteratorFactorySelector(iteratorFactory, maxiter,this, !endless)

  private val pictureView: FractalPictureViewBase[Coordinate] =
    if (resizeable)
      new FractalScaledPictureView[Coordinate](picture, pairedController)
    else
      new FractalPictureView[Coordinate](picture, pairedController)

  private val controlView: JComponent = new FractalEditView(model, controller)

  private def getPanel(view: JComponent, controlView: JComponent): JPanel = {
    val panel = new JPanel(new GridBagLayout)
    val c = new GridBagConstraints
    c.weighty = 1.0
    c.weightx = 1.0
    c.gridwidth = GridBagConstraints.REMAINDER
    c.fill = GridBagConstraints.BOTH
    panel.add(view, c)
    c.weightx = 0.0
    c.weighty = 0.0
    c.fill = GridBagConstraints.NONE
    panel.add(controlView, c)
    panel
  }

  private val panel = getPanel(pictureView, controlView)

  private def createGUI(): Unit = {
    val c = new GridBagConstraints
    c.weighty = 1.0
    c.weightx = 1.0
    c.gridwidth = GridBagConstraints.REMAINDER
    c.fill = GridBagConstraints.BOTH
    add(panel, c)
    c.weightx = 0.0
    c.weighty = 0.0
    c.fill = GridBagConstraints.NONE
    add(paletteSelector, c)
    add(iteratorSelector, c)

    if (resizeable) {
      val sizeSelector = new FractalSizeSelector(model, controller)
      add(sizeSelector, c)
    }
  }

  createGUI()

  controller.startCalculation()


  override def setPalette(palette: Palette): Unit = {
    this.palette = palette
    pictureView.setPalette(palette)
  }

  override def getPalette: Palette = palette

  override def setIteratorFactory(iteratorFactory: FractalIteratorFactory[Coordinate]): Unit =
    controller.setIteratorFactory(iteratorFactory)

  override def setMaxiter(maxiter: Int): Unit =
    controller.setMaxiter(maxiter)

  override def setIteratorData(iteratorFactory: FractalIteratorFactory[Coordinate], maxiter: Int): Unit =
    controller.setIteratorData(iteratorFactory, maxiter)

  def getIteratorSelector: FractalScalaIteratorFactorySelector = iteratorSelector

  def getView: FractalPictureViewBase[Coordinate] = pictureView

  def getController: Fractal2DIterateController = controller

}
