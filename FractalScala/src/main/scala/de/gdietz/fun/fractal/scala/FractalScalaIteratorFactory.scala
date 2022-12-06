package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.formula.{FractalIterator, FractalIteratorFactory, ValidityTestable, ValidityTest => JavaValidityTest}
import de.gdietz.fun.fractal.fuzzy.Fuzzy
import de.gdietz.fun.fractal.scala.util.{Complex, Quaternion, Vector3D}
import de.gdietz.fun.fractal.util.{Coordinate, Coordinate3D, Coordinate4D, Tuple}

import scala.language.implicitConversions
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox
import scala.util.control.NonFatal

trait FractalScalaIteratorFactory[T <: Tuple[T], C]
  extends FractalIteratorFactory[T]
    with ValidityTestable[Unit]
    with JavaValidityTest[Unit]
    with Fuzzy {

  def code: String

  protected def fullCode: String

  @inline implicit def convertTuple(x: T): C

  def compile(): FractalIteratorDefinition[C] = {
    val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()
    val tree = toolbox.parse(fullCode)
    val definition0 = toolbox.compile(tree)
    definition0() match {
      case d: FractalIteratorDefinition[C] @unchecked => d
      case _ => sys.error("compile result is not a FractalIteratorDefinition")
    }
  }

  private[scala] lazy val tryDefinition: Either[Throwable, FractalIteratorDefinition[C]] =
    try
      Right(compile())
    catch {
      case NonFatal(e) =>  Left(e)
    }

  private lazy val definition: FractalIteratorDefinition[C] =
    tryDefinition match {
      case Right(definition1) => definition1
      case Left(e) =>
        e.printStackTrace()
        FractalIteratorDefinition.invalid
    }

  private class FractalScalaIterator(c: T, p: T, lambda: Double)
    extends FractalIterator[T] {

    private val definitionInitialized = definition(c, p, lambda)
    private val validityTest = definition.validityTest(lambda)

    private var z: definition.C = definitionInitialized.z0

    override def isValid: Boolean = validityTest.isValid(z)

    override def isSurvivor: Boolean = false

    override def iterate(): Unit = z = definitionInitialized.zNext(z)

    override def getCoordinate: T = c

  }


  private var lambda: Double = 100.0
  private var epsilon: Double = 0.0


  override def get(c: T, p: T): FractalIterator[T] =
    new FractalScalaIterator(c, p, lambda)

  override def getTest: JavaValidityTest[Unit] =
    this

  override def setTest(test: JavaValidityTest[Unit]): Unit = {
    test match {
      case fuzzy: Fuzzy =>
        lambda = fuzzy.getLambda
        epsilon = fuzzy.getEpsilon
      case _ =>
    }
  }

  override def isValid(x: Unit): Boolean = false

  override def isSurvivor(x: Unit): Boolean = false

  override def setLambda(lambda: Double): Unit = this.lambda = lambda

  override def getLambda: Double = lambda

  override def setEpsilon(epsilon: Double): Unit = this.epsilon = epsilon

  override def getEpsilon: Double = epsilon

}


case class ComplexFractalScalaIteratorFactory(override val code: String)
  extends FractalScalaIteratorFactory[Coordinate, Complex] {

  override protected def fullCode: String =
    s"""import _root_.de.gdietz.fun.fractal.scala.ComplexFractalIteratorDefinition
       |import _root_.de.gdietz.fun.fractal.scala.util._
       |import _root_.de.gdietz.fun.fractal.scala.util.Complex.i
       |import _root_.de.gdietz.fun.fractal.scala.util.Quaternion.{j, k}
       |import _root_.de.gdietz.fun.fractal.scala.util.implicits._
       |import _root_.de.gdietz.fun.fractal.util.Coordinate
       |import _root_.de.gdietz.fun.fractal.scala.ComplexFractalIteratorDefinition._
       |{
       |$code
       |}: ComplexFractalIteratorDefinition
       |""".stripMargin

  @inline override def convertTuple(x: Coordinate): Complex = x

}

object ComplexFractalScalaIteratorFactory {

  val simpleCode: String =
    """normed((c, p) => p)((c, p) => z => z.sqr + c)"""

  val z3z2Code: String =
    """any { (c, p) =>
      |  val p3 = 1.0 + p
      |  val p2 = 1.0 - p
      |  val z0 = p2 / (-1.5 * p3)
      |  ComplexVector2(Complex.zero, z0)
      |} { (c, p) =>
      |  val p3 = 1.0 + p
      |  val p2 = 1.0 - p
      |  z => p3 *! z.cube + p2 *!: z.sqr +! c
      |}""".stripMargin


  // more interesting formula for default...
  val defaultCode: String = z3z2Code

  val default: ComplexFractalScalaIteratorFactory =
    ComplexFractalScalaIteratorFactory(defaultCode)

}


case class QuaternionFractalScalaIteratorFactory(override val code: String)
  extends FractalScalaIteratorFactory[Coordinate4D, Quaternion] {

  override protected def fullCode: String =
    s"""import _root_.de.gdietz.fun.fractal.scala.QuaternionFractalIteratorDefinition
       |import _root_.de.gdietz.fun.fractal.scala.util._
       |import _root_.de.gdietz.fun.fractal.scala.util.Complex.i
       |import _root_.de.gdietz.fun.fractal.scala.util.Quaternion.{j, k}
       |import _root_.de.gdietz.fun.fractal.scala.util.implicits._
       |import _root_.de.gdietz.fun.fractal.util.Coordinate4D
       |import _root_.de.gdietz.fun.fractal.scala.QuaternionFractalIteratorDefinition._
       |{
       |$code
       |}: QuaternionFractalIteratorDefinition
       |""".stripMargin

  @inline override def convertTuple(x: Coordinate4D): Quaternion = x

}

object QuaternionFractalScalaIteratorFactory {

  val simpleCode: String =
    """normed((c, p) => p)((c, p) => z => z.sqr + c)"""

  val defaultCode: String = simpleCode

  val default: QuaternionFractalScalaIteratorFactory =
    QuaternionFractalScalaIteratorFactory(defaultCode)

}


case class Vector3DFractalScalaIteratorFactory(override val code: String)
  extends FractalScalaIteratorFactory[Coordinate3D, Vector3D] {

  override protected def fullCode: String =
    s"""import _root_.de.gdietz.fun.fractal.scala.Vector3DFractalIteratorDefinition
       |import _root_.de.gdietz.fun.fractal.scala.util._
       |import _root_.de.gdietz.fun.fractal.scala.util.Complex.i
       |import _root_.de.gdietz.fun.fractal.scala.util.Quaternion.{j, k}
       |import _root_.de.gdietz.fun.fractal.scala.util.implicits._
       |import _root_.de.gdietz.fun.fractal.util.Coordinate3D
       |import _root_.de.gdietz.fun.fractal.scala.Vector3DFractalIteratorDefinition._
       |{
       |$code
       |}: Vector3DFractalIteratorDefinition
       |""".stripMargin

  @inline override def convertTuple(x: Coordinate3D): Vector3D = x

}

object Vector3DFractalScalaIteratorFactory {

  val simpleCode: String =
    """normed { (c, p) =>
      |  p.toQuaternion
      |} { (c, p) =>
      |  val cq = c.toQuaternion
      |  z => z.sqr + cq
      |}""".stripMargin

  val defaultCode: String = simpleCode

  val default: Vector3DFractalScalaIteratorFactory =
    Vector3DFractalScalaIteratorFactory(defaultCode)

}
