package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.controller.FractalIteratorManager
import de.gdietz.fun.fractal.gui.{AdvancedFractalIteratorFactorySelector, AdvancedFractalIteratorFactorySelectorProvider}
import de.gdietz.fun.fractal.util.{Coordinate, Tuple}

class AdvancedFractalScalaIteratorFactorySelectorProvider
  extends AdvancedFractalIteratorFactorySelectorProvider {

  override def createIteratorFactorySelectorProvider[T <: Tuple[T]](clazz: Class[_ <: T], listener: FractalIteratorManager[T], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[T] =
    if (clazz == classOf[Coordinate]) {
      val listener0 = listener.asInstanceOf[FractalIteratorManager[Coordinate]]
      val provider0 = createIteratorFactorySelectorProvider(listener0, askMaxiter)
      provider0.asInstanceOf[AdvancedFractalIteratorFactorySelector[T]]
    }  else
      new AdvancedFractalIteratorFactorySelector(listener, askMaxiter)

  def createIteratorFactorySelectorProvider[T <: Tuple[T]](listener: FractalIteratorManager[T], askMaxiter: Boolean)
                                                          (implicit ev: CanProvideIteratorFactorySelector[T]): AdvancedFractalIteratorFactorySelector[T] =
    ev.createIteratorFactorySelectorProvider(listener, askMaxiter)

}


trait CanProvideIteratorFactorySelector[T <: Tuple[T]] {

  def createIteratorFactorySelectorProvider(listener: FractalIteratorManager[T], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[T]

}

object CanProvideIteratorFactorySelector extends CanProvideIteratorFactorySelectorInstances

private[scala] sealed abstract class CanProvideIteratorFactorySelectorInstances {

  implicit def canProvideCoordinateIteratorFactorySelector: CanProvideIteratorFactorySelector[Coordinate] =
    new CanProvideIteratorFactorySelector[Coordinate] {
      override def createIteratorFactorySelectorProvider(listener: FractalIteratorManager[Coordinate], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[Coordinate] =
        new AdvancedFractalScalaIteratorFactorySelector(listener, askMaxiter)
    }

}
