package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.controller.FractalIteratorManager
import de.gdietz.fun.fractal.gui.{AdvancedFractalIteratorFactorySelector, AdvancedFractalIteratorFactorySelectorProvider}
import de.gdietz.fun.fractal.util.{Coordinate, Coordinate3D, Coordinate4D, Tuple}

class AdvancedFractalScalaIteratorFactorySelectorProvider
  extends AdvancedFractalIteratorFactorySelectorProvider {

  override def createIteratorFactorySelectorProvider[T <: Tuple[T]](clazz: Class[_ <: T], listener: FractalIteratorManager[T], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[T] =
    if (clazz == classOf[Coordinate]) {
      val listener0 = listener.asInstanceOf[FractalIteratorManager[Coordinate]]
      val provider0 = createIteratorFactorySelectorProvider(listener0, askMaxiter)
      provider0.asInstanceOf[AdvancedFractalIteratorFactorySelector[T]]
    } else if (clazz == classOf[Coordinate4D]) {
      val listener0 = listener.asInstanceOf[FractalIteratorManager[Coordinate4D]]
      val provider0 = createIteratorFactorySelectorProvider(listener0, askMaxiter)
      provider0.asInstanceOf[AdvancedFractalIteratorFactorySelector[T]]
    } else if (clazz == classOf[Coordinate3D]) {
      val listener0 = listener.asInstanceOf[FractalIteratorManager[Coordinate3D]]
      val provider0 = createIteratorFactorySelectorProvider(listener0, askMaxiter)
      provider0.asInstanceOf[AdvancedFractalIteratorFactorySelector[T]]
    } else
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
    new CanProvideIteratorFactorySelector[Coordinate] with Serializable {
      override def createIteratorFactorySelectorProvider(listener: FractalIteratorManager[Coordinate], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[Coordinate] =
        new ComplexAdvancedFractalScalaIteratorFactorySelector(listener, askMaxiter)
    }

  implicit def canProvideCoordinate4DIteratorFactorySelector: CanProvideIteratorFactorySelector[Coordinate4D] =
    new CanProvideIteratorFactorySelector[Coordinate4D] with Serializable {
      override def createIteratorFactorySelectorProvider(listener: FractalIteratorManager[Coordinate4D], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[Coordinate4D] =
        new QuaternionAdvancedFractalScalaIteratorFactorySelector(listener, askMaxiter)
    }

  implicit def canProvideCoordinate3DIteratorFactorySelector: CanProvideIteratorFactorySelector[Coordinate3D] =
    new CanProvideIteratorFactorySelector[Coordinate3D] with Serializable {
      override def createIteratorFactorySelectorProvider(listener: FractalIteratorManager[Coordinate3D], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[Coordinate3D] =
        new Vector3DAdvancedFractalScalaIteratorFactorySelector(listener, askMaxiter)
    }

}
