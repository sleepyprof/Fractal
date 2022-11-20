package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.formula.{FractalIterator, FractalIteratorFactory, ValidityTest => JavaValidityTest, ValidityTestable}
import de.gdietz.fun.fractal.fuzzy.Fuzzy
import de.gdietz.fun.fractal.scala.util.Complex
import de.gdietz.fun.fractal.util.Coordinate

import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox
import scala.util.control.NonFatal

case class FractalScalaIteratorFactory(code: String)
  extends FractalIteratorFactory[Coordinate]
    with ValidityTestable[Unit]
    with JavaValidityTest[Unit]
    with Fuzzy {

  def compile(): ComplexFractalIteratorDefinition = {
    val fullCode =
      s"""import _root_.de.gdietz.fun.fractal.scala.ComplexFractalIteratorDefinition
         |import _root_.de.gdietz.fun.fractal.scala.util._
         |import _root_.de.gdietz.fun.fractal.scala.util.Complex.i
         |import _root_.de.gdietz.fun.fractal.scala.util.implicits._
         |import _root_.de.gdietz.fun.fractal.util.Coordinate
         |{
         |$code
         |}: ComplexFractalIteratorDefinition
         |""".stripMargin

    val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()
    val tree = toolbox.parse(fullCode)
    val definition0 = toolbox.compile(tree)
    definition0() match {
      case d: FractalIteratorDefinition[Complex] @unchecked => d
      case _ => sys.error("compile result is not a FractalIteratorDefinition")
    }
  }

  private[scala] lazy val tryDefinition: Either[Throwable, ComplexFractalIteratorDefinition] =
    try
      Right(compile())
    catch {
      case NonFatal(e) =>  Left(e)
    }

  private lazy val definition: ComplexFractalIteratorDefinition =
    tryDefinition match {
      case Right(definition1) => definition1
      case Left(e) =>
        e.printStackTrace()
        FractalIteratorDefinition.invalid
    }

  private class FractalScalaIterator(c: Coordinate, p: Coordinate, lambda: Double)
    extends FractalIterator[Coordinate] {

    private val definitionInitialized = definition(c, p, lambda)
    private val validityTest = definition.validityTest(lambda)

    private var z: definition.C = definitionInitialized.z0

    override def isValid: Boolean = validityTest.isValid(z)

    override def isSurvivor: Boolean = false

    override def iterate(): Unit = z = definitionInitialized.zNext(z)

    override def getCoordinate: Coordinate = c

  }


  private var lambda: Double = 100.0
  private var epsilon: Double = 0.0


  override def get(c: Coordinate, p: Coordinate): FractalIterator[Coordinate] =
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

object FractalScalaIteratorFactory {

  val simpleCode: String =
    """ComplexFractalIteratorDefinition { (c, p) =>
      |  p
      |} { (c, p, lambda) =>
      |  z => z.sqr + c
      |} { lambda =>
      |  val l2 = lambda * lambda
      |  z => z.normSqr <= l2
      |}""".stripMargin

  val z3z2Code: String =
    """ComplexFractalIteratorDefinition { (c, p) =>
      |  val p3 = 1.0 + p
      |  val p2 = 1.0 - p
      |  val z0 = p2 / (-1.5 * p3)
      |  ComplexVector2(Complex.zero, z0)
      |} { (c, p, lambda) =>
      |  val p3 = 1.0 + p
      |  val p2 = 1.0 - p
      |  val l2 = lambda * lambda
      |  z => (p3 *! z.cube + p2 *!: z.sqr +! c).filterNumber(_.normSqr <= l2)
      |} { lambda =>
      |  z => z.exists(_.isNumber)
      |}""".stripMargin


  // more interesting formula for default...
  val defaultCode: String = z3z2Code

  val default: FractalScalaIteratorFactory =
    FractalScalaIteratorFactory(defaultCode)

}
