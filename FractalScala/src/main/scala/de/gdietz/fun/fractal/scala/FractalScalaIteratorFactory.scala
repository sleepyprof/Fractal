package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.formula.{FractalIterator, FractalIteratorFactory}
import de.gdietz.fun.fractal.util.Coordinate

import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox
import scala.util.control.NonFatal

case class FractalScalaIteratorFactory(code: String) extends FractalIteratorFactory[Coordinate] {

  def compile(): FractalIteratorDefinition = {
    val fullCode =
      s"""import _root_.de.gdietz.fun.fractal.scala.FractalIteratorDefinition
         |import _root_.de.gdietz.fun.fractal.scala.util._
         |import _root_.de.gdietz.fun.fractal.scala.util.Complex.i
         |import _root_.de.gdietz.fun.fractal.scala.util.implicits._
         |import _root_.de.gdietz.fun.fractal.util.Coordinate
         |{
         |$code
         |}: FractalIteratorDefinition
         |""".stripMargin

    val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()
    val tree = toolbox.parse(fullCode)
    val definition0 = toolbox.compile(tree)
    definition0() match {
      case d: FractalIteratorDefinition => d
      case _ => sys.error("compile result is not a FractalIteratorDefinition")
    }
  }

  private[scala] lazy val tryDefinition: Either[Throwable, FractalIteratorDefinition] =
    try
      Right(compile())
    catch {
      case NonFatal(e) =>  Left(e)
    }

  private lazy val definition: FractalIteratorDefinition =
    tryDefinition match {
      case Right(definition1) => definition1
      case Left(e) =>
        e.printStackTrace()
        FractalIteratorDefinition.invalid
    }

  private class FractalScalaIterator(c: Coordinate, p: Coordinate) extends FractalIterator[Coordinate] {

    private var z: definition.C = definition.z0(c, p)
    private lazy val zNext0: definition.C => definition.C = definition.zNext(c, p)

    override def isValid: Boolean = definition.isValid(z)

    override def isSurvivor: Boolean = false

    override def iterate(): Unit = z = zNext0(z)

    override def getCoordinate: Coordinate = c

  }

  override def get(c: Coordinate, p: Coordinate): FractalIterator[Coordinate] =
    new FractalScalaIterator(c, p)

}

object FractalScalaIteratorFactory {

  val simpleCode: String =
    """( (c: Complex, p: Complex) => (
      |    p,
      |    (z: Complex) => z.sqr + c
      |  ),
      |  (z: Complex) => z.normSqr <= 4.0
      |)""".stripMargin

  val z3z2Code: String =
    """( (c: Complex, p: Complex) => {
      |  val p3 = 1.0 + p
      |  val p2 = 1.0 - p
      |  val z0 = p2 / (-1.5 * p3)
      |  (
      |    ComplexVector2(Complex.zero, z0),
      |    (z: ComplexVector2) =>
      |      (p3 *! z.cube + p2 *!: z.sqr +! c).filterNumber(_.normSqr <= 400.0)
      |  )},
      |  (z: ComplexVector2) => z.exists(_.isNumber)
      |)""".stripMargin


  // more interesting formula for default...
  val default: FractalScalaIteratorFactory =
    FractalScalaIteratorFactory(z3z2Code)

}
