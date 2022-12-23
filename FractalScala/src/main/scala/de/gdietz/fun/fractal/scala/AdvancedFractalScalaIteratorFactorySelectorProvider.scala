package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.controller.FractalIteratorManager
import de.gdietz.fun.fractal.gui.{AdvancedFractalIteratorFactorySelector, AdvancedFractalIteratorFactorySelectorProvider, AdvancedFractalPreciseIteratorFactorySelector, AdvancedFractalPreciseIteratorFactorySelectorProvider}
import de.gdietz.fun.fractal.util.{BigCoordinate, Coordinate, Coordinate3D, Coordinate4D, Tuple}

import scala.reflect.ClassTag

class AdvancedFractalScalaIteratorFactorySelectorProvider
  extends AdvancedFractalIteratorFactorySelectorProvider
  with AdvancedFractalPreciseIteratorFactorySelectorProvider {

  override def createIteratorFactorySelectorProvider[T <: Tuple[T]](clazz: Class[_ <: T], listener: FractalIteratorManager[T], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[T] =
    checkCreateIteratorFactorySelectorProvider[T, Coordinate](clazz, listener, askMaxiter) orElse
      checkCreateIteratorFactorySelectorProvider[T, Coordinate4D](clazz, listener, askMaxiter) orElse
      checkCreateIteratorFactorySelectorProvider[T, Coordinate3D](clazz, listener, askMaxiter) orElse
      checkCreateIteratorFactorySelectorProvider[T, BigCoordinate](clazz, listener, askMaxiter) getOrElse
      new AdvancedFractalIteratorFactorySelector(listener, askMaxiter)

  override def createPreciseIteratorFactorySelectorProvider[T <: Tuple[T]](clazz: Class[_ <: T], listener: FractalIteratorManager[T], askMaxiter: Boolean): AdvancedFractalPreciseIteratorFactorySelector[T] =
    checkCreatePreciseIteratorFactorySelectorProvider[T, BigCoordinate](clazz, listener, askMaxiter) getOrElse
      new AdvancedFractalPreciseIteratorFactorySelector(listener, askMaxiter)

  def createIteratorFactorySelectorProvider[T <: Tuple[T]](listener: FractalIteratorManager[T], askMaxiter: Boolean)
                                                          (implicit ev: CanProvideIteratorFactorySelector[T]): AdvancedFractalIteratorFactorySelector[T] =
    ev.createIteratorFactorySelectorProvider(listener, askMaxiter)

  def createPreciseIteratorFactorySelectorProvider[T <: Tuple[T]](listener: FractalIteratorManager[T], askMaxiter: Boolean)
                                                                 (implicit ev: CanProvidePreciseIteratorFactorySelector[T]): AdvancedFractalPreciseIteratorFactorySelector[T] =
    ev.createPreciseIteratorFactorySelectorProvider(listener, askMaxiter)

  private def checkCreateIteratorFactorySelectorProvider[T <: Tuple[T], T0 <: Tuple[T0]](clazz: Class[_ <: T], listener: FractalIteratorManager[T], askMaxiter: Boolean)
                                                                                        (implicit ev: CanProvideIteratorFactorySelector[T0], tag: ClassTag[T0]): Option[AdvancedFractalIteratorFactorySelector[T]] =
    if (clazz == tag.runtimeClass) {
      val listener0 = listener.asInstanceOf[FractalIteratorManager[T0]]
      val provider0 = createIteratorFactorySelectorProvider(listener0, askMaxiter)
      Some(provider0.asInstanceOf[AdvancedFractalIteratorFactorySelector[T]])
    } else None

  private def checkCreatePreciseIteratorFactorySelectorProvider[T <: Tuple[T], T0 <: Tuple[T0]](clazz: Class[_ <: T], listener: FractalIteratorManager[T], askMaxiter: Boolean)
                                                                                               (implicit ev: CanProvidePreciseIteratorFactorySelector[T0], tag: ClassTag[T0]): Option[AdvancedFractalPreciseIteratorFactorySelector[T]] =
    if (clazz == tag.runtimeClass) {
      val listener0 = listener.asInstanceOf[FractalIteratorManager[T0]]
      val provider0 = createPreciseIteratorFactorySelectorProvider(listener0, askMaxiter)
      Some(provider0.asInstanceOf[AdvancedFractalPreciseIteratorFactorySelector[T]])
    } else None

}


trait CanProvideIteratorFactorySelector[T <: Tuple[T]] {

  def createIteratorFactorySelectorProvider(listener: FractalIteratorManager[T], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[T]

}

trait CanProvidePreciseIteratorFactorySelector[T <: Tuple[T]]
  extends CanProvideIteratorFactorySelector[T] {

  def createPreciseIteratorFactorySelectorProvider(listener: FractalIteratorManager[T], askMaxiter: Boolean): AdvancedFractalPreciseIteratorFactorySelector[T]

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

  implicit def canProvideBigCoordinateIteratorFactorySelector: CanProvidePreciseIteratorFactorySelector[BigCoordinate] =
    new CanProvidePreciseIteratorFactorySelector[BigCoordinate] with Serializable {
      override def createIteratorFactorySelectorProvider(listener: FractalIteratorManager[BigCoordinate], askMaxiter: Boolean): AdvancedFractalIteratorFactorySelector[BigCoordinate] =
        new BigComplexAdvancedFractalScalaIteratorFactorySelector(listener, askMaxiter)
      override def createPreciseIteratorFactorySelectorProvider(listener: FractalIteratorManager[BigCoordinate], askMaxiter: Boolean): AdvancedFractalPreciseIteratorFactorySelector[BigCoordinate] =
        new BigComplexAdvancedFractalScalaPreciseIteratorFactorySelector(listener, askMaxiter)
    }

}
