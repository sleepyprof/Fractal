package de.gdietz.fun.fractal.scala

import de.gdietz.fun.fractal.formula.{FractalIterator, FractalIteratorFactory}
import de.gdietz.fun.fractal.util.Coordinate

import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox
import scala.util.control.NonFatal

case class FractalScalaIteratorFactory(code: String) extends FractalIteratorFactory[Coordinate] {

  def compile(): FractalIteratorDefinition = {
    val fullCode =
      s"""import _root_.de.gdietz.fun.fractal.scala.util.Complex
         |import _root_.de.gdietz.fun.fractal.util.Coordinate
         |import _root_.de.gdietz.fun.fractal.scala.FractalIteratorDefinition
         |import _root_.de.gdietz.fun.fractal.scala.util.Complex.i
         |{
         |$code
         |}: FractalIteratorDefinition
         |""".stripMargin

    val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()
    val tree = toolbox.parse(fullCode)
    toolbox.typecheck(tree, pt = typeOf[FractalIteratorDefinition])
    val definition0 = toolbox.compile(tree)
    definition0().asInstanceOf[FractalIteratorDefinition]
  }

  private lazy val definition: FractalIteratorDefinition =
    try
      compile()
    catch {
      case NonFatal(e) =>
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

  val default: FractalScalaIteratorFactory =
    FractalScalaIteratorFactory(
      """( (c: Complex, p: Complex) => (
        |    p,
        |    (z: Complex) => z.sqr + c
        |  ),
        |  (z: Complex) => z.normSqr <= 4.0
        |)""".stripMargin)

}
