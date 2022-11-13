package de.gdietz.fun.fractal.scala.util.test

import de.gdietz.fun.fractal.scala.util._
import de.gdietz.fun.fractal.scala.util.implicits._

object ComplexTest {

  def main(args: Array[String]): Unit = {
    val r: Double = 2.0
    val c: Complex = Complex(1.0, 1.0)
    val co: OptComplex = Complex(1.0, 2.0)
    val o: OptComplex = OptComplex.none
    val q: Quaternion = Quaternion(1.0, 2.0, 3.0, 4.0)
    val qo: OptQuaternion = Quaternion(1.0, 2.0, 3.0, 4.0)

    println("r  + r  = " + (r  + r ))
    println("r  + c  = " + (r  + c ))
    println("r  + co = " + (r  + co))
    println("r  + o  = " + (r  + o ))
    println("r  + q  = " + (r  + q ))
    println("r  + qo = " + (r  + qo))

    println("-----")

    println("c  + r  = " + (c  + r ))
    println("c  + c  = " + (c  + c ))
    println("c  + co = " + (c  + co))
    println("c  + o  = " + (c  + o ))
    println("c  + q  = " + (c  + q ))
    println("c  + qo = " + (c  + qo))

    println("-----")

    println("co + r  = " + (co + r ))
    println("co + c  = " + (co + c ))
    println("co + co = " + (co + co))
    println("co + o  = " + (co + o ))
    println("co + q  = " + (co + q ))
    println("co + qo = " + (co + qo))

    println("-----")

    println("o  + r  = " + (o  + r ))
    println("o  + c  = " + (o  + c ))
    println("o  + co = " + (o  + co))
    println("o  + o  = " + (o  + o ))
    println("o  + q  = " + (o  + q ))
    println("o  + qo = " + (o  + qo))

    println("-----")

    println("q  + r  = " + (q  + r ))
    println("q  + c  = " + (q  + c ))
    println("q  + co = " + (q  + co))
    println("q  + o  = " + (q  + o ))
    println("q  + q  = " + (q  + q ))
    println("q  + qo = " + (q  + qo))

    println("-----")

    println("qo + r  = " + (qo + r ))
    println("qo + c  = " + (qo + c ))
    println("qo + co = " + (qo + co))
    println("qo + o  = " + (qo + o ))
    println("qo + q  = " + (qo + q ))
    println("qo + qo = " + (qo + qo))

    println("-----")
    println("-----")

    val v = ComplexVector2(c, co)

    println("v  +  v  = " + (v  + v ))
    println("v  +! c  = " + (v  +! c ))
    println("c  +! v  = " + (c  +! v ))

    println("-----")

    println("c.roots2        = " + c.roots2)
    println("c.roots(2)      = " + c.roots(2))
    println("c.roots(3)      = " + c.roots(3))
    println("c.roots(3).cube = " + c.roots(3).cube)
    println("c.roots2.sqr    = " + c.roots2.sqr)

  }

}
