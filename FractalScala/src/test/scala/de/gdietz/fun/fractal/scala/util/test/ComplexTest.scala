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

    println("c ** 3     = " + (c ** 3))
    println("c.pow(3)   = " + c.pow(3))
    println("c ** 3.0   = " + (c ** 3.0))
    println("c.pow(3.0) = " + c.pow(3.0))

    println("-----")

    val v = ComplexVector2(c, co)

    println("v  +  v  = " + (v  +  v ))
    println("v  +! c  = " + (v  +! c ))
    println("c  +! v  = " + (c  +! v ))
    println("v  +  c  = " + (v  +  c ))
    //println("c  +  v  = " + (c  +  v ))   // TODO implicit injection (see below) not working. Why?
    println("c  +  v  = " + (toOptHigherNumberOps(c)  +  v))
    println("v  +! co = " + (v  +! co))
    println("co +! v  = " + (co +! v))
    println("v  +  co = " + (v  + co))
    //println("co +  v  = " + (co + v ))   // TODO implicit injection (see below) not working. Why?
    println("co +  v  = " + (toOptHigherNumberOps(co) + v))

    println("-----")

    println("c .roots2        = " + c .roots2)
    println("c .roots(2)      = " + c .roots(2))
    println("c .roots(3)      = " + c .roots(3))
    println("c .roots2.sqr    = " + c .roots2.sqr)
    println("c .roots(3).cube = " + c .roots(3).cube)
    println("co.roots2        = " + co.roots2)
    println("co.roots(2)      = " + co.roots(2))
    println("co.roots(3)      = " + co.roots(3))
    println("co.roots2.sqr    = " + co.roots2.sqr)
    println("co.roots(3).cube = " + co.roots(3).cube)

    println("-----")

    println("v  **  v  = " + (v  **  v ))
    println("v  **  c  = " + (v  **  c ))
    println("v1 **  v1 = " + (v.x1 ** v.x1))
    println("v2 **  v2 = " + (v.x2 ** v.x2))
    println("v1 **  c  = " + (v.x1 ** c))
    println("v2 **  c  = " + (v.x2 ** c))

    println("-----")

    println("pauli1                      = " +  RealMatrix2.pauli1                         )
    println("(i * pauli2)                = " +  RealMatrix2.iPauli2                        )
    println("pauli3                      = " +  RealMatrix2.pauli3                         )
    println("pauli1       * pauli1       = " + (RealMatrix2.pauli1  * RealMatrix2.pauli1 ))
    println("pauli1       * (i * pauli2) = " + (RealMatrix2.pauli1  * RealMatrix2.iPauli2))
    println("pauli1       * pauli3       = " + (RealMatrix2.pauli1  * RealMatrix2.pauli3 ))
    println("(i * pauli2) * pauli1       = " + (RealMatrix2.iPauli2 * RealMatrix2.pauli1 ))
    println("(i * pauli2) * (i * pauli2) = " + (RealMatrix2.iPauli2 * RealMatrix2.iPauli2))
    println("(i * pauli2) * pauli3       = " + (RealMatrix2.iPauli2 * RealMatrix2.pauli3 ))
    println("pauli3       * pauli1       = " + (RealMatrix2.pauli3  * RealMatrix2.pauli1 ))
    println("pauli3       * (i * pauli2) = " + (RealMatrix2.pauli3  * RealMatrix2.iPauli2))
    println("pauli3       * pauli3       = " + (RealMatrix2.pauli3  * RealMatrix2.pauli3 ))

    println("-----")

    println("pauli1          = " +  ComplexMatrix2.pauli1                         )
    println("pauli2          = " +  ComplexMatrix2.pauli2                         )
    println("pauli3          = " +  ComplexMatrix2.pauli3                         )
    println("pauli1 * pauli1 = " + (ComplexMatrix2.pauli1 * ComplexMatrix2.pauli1))
    println("pauli1 * pauli2 = " + (ComplexMatrix2.pauli1 * ComplexMatrix2.pauli2))
    println("pauli1 * pauli3 = " + (ComplexMatrix2.pauli1 * ComplexMatrix2.pauli3))
    println("pauli2 * pauli1 = " + (ComplexMatrix2.pauli2 * ComplexMatrix2.pauli1))
    println("pauli2 * pauli2 = " + (ComplexMatrix2.pauli2 * ComplexMatrix2.pauli2))
    println("pauli2 * pauli3 = " + (ComplexMatrix2.pauli2 * ComplexMatrix2.pauli3))
    println("pauli3 * pauli1 = " + (ComplexMatrix2.pauli3 * ComplexMatrix2.pauli1))
    println("pauli3 * pauli2 = " + (ComplexMatrix2.pauli3 * ComplexMatrix2.pauli2))
    println("pauli3 * pauli3 = " + (ComplexMatrix2.pauli3 * ComplexMatrix2.pauli3))

    println("-----")

    val m = RealMatrix2(1.0, 2.0, 3.0, 4.0)

    println("m * pauli1       = " + (m * RealMatrix2.pauli1))
    println("m * (i * pauli2) = " + (m * RealMatrix2.iPauli2))
    println("m * pauli3       = " + (m * RealMatrix2.pauli3))

    println("-----")

    val qm = QuaternionMatrix2(Quaternion.i, Quaternion.j, Quaternion.k, Quaternion.i + Quaternion.k)

    println("qm * qm.inverse = " + (qm * qm.inverse))
    println("qm.inverse * qm = " + (qm.inverse * qm))

    println("-----")

    println("dirac0                      = " +  RealMatrix4.dirac0                        )
    println("dirac1                      = " +  RealMatrix4.dirac1                        )
    println("(i * dirac2)                = " +  RealMatrix4.iDirac2                       )
    println("dirac3                      = " +  RealMatrix4.dirac3                        )
    println("dirac1       * dirac1       = " + (RealMatrix4.dirac1  * RealMatrix4.dirac1 ))
    println("dirac1       * (i * dirac2) = " + (RealMatrix4.dirac1  * RealMatrix4.iDirac2))
    println("dirac1       * dirac3       = " + (RealMatrix4.dirac1  * RealMatrix4.dirac3 ))
    println("(i * dirac2) * dirac1       = " + (RealMatrix4.iDirac2 * RealMatrix4.dirac1 ))
    println("(i * dirac2) * (i * dirac2) = " + (RealMatrix4.iDirac2 * RealMatrix4.iDirac2))
    println("(i * dirac2) * dirac3       = " + (RealMatrix4.iDirac2 * RealMatrix4.dirac3 ))
    println("dirac3       * dirac1       = " + (RealMatrix4.dirac3  * RealMatrix4.dirac1 ))
    println("dirac3       * (i * dirac2) = " + (RealMatrix4.dirac3  * RealMatrix4.iDirac2))
    println("dirac3       * dirac3       = " + (RealMatrix4.dirac3  * RealMatrix4.dirac3 ))

    println("-----")

    println("dirac0          = " +  ComplexMatrix4.dirac0                         )
    println("dirac1          = " +  ComplexMatrix4.dirac1                         )
    println("dirac2          = " +  ComplexMatrix4.dirac2                         )
    println("dirac3          = " +  ComplexMatrix4.dirac3                         )
    println("dirac0 * dirac0 = " + (ComplexMatrix4.dirac0 * ComplexMatrix4.dirac0))
    println("dirac0 * dirac1 = " + (ComplexMatrix4.dirac0 * ComplexMatrix4.dirac1))
    println("dirac0 * dirac2 = " + (ComplexMatrix4.dirac0 * ComplexMatrix4.dirac2))
    println("dirac0 * dirac3 = " + (ComplexMatrix4.dirac0 * ComplexMatrix4.dirac3))
    println("dirac1 * dirac0 = " + (ComplexMatrix4.dirac1 * ComplexMatrix4.dirac0))
    println("dirac1 * dirac1 = " + (ComplexMatrix4.dirac1 * ComplexMatrix4.dirac1))
    println("dirac1 * dirac2 = " + (ComplexMatrix4.dirac1 * ComplexMatrix4.dirac2))
    println("dirac1 * dirac3 = " + (ComplexMatrix4.dirac1 * ComplexMatrix4.dirac3))
    println("dirac2 * dirac0 = " + (ComplexMatrix4.dirac2 * ComplexMatrix4.dirac0))
    println("dirac2 * dirac1 = " + (ComplexMatrix4.dirac2 * ComplexMatrix4.dirac1))
    println("dirac2 * dirac2 = " + (ComplexMatrix4.dirac2 * ComplexMatrix4.dirac2))
    println("dirac2 * dirac3 = " + (ComplexMatrix4.dirac2 * ComplexMatrix4.dirac3))
    println("dirac3 * dirac0 = " + (ComplexMatrix4.dirac3 * ComplexMatrix4.dirac0))
    println("dirac3 * dirac1 = " + (ComplexMatrix4.dirac3 * ComplexMatrix4.dirac1))
    println("dirac3 * dirac2 = " + (ComplexMatrix4.dirac3 * ComplexMatrix4.dirac2))
    println("dirac3 * dirac3 = " + (ComplexMatrix4.dirac3 * ComplexMatrix4.dirac3))

    println("dirac5          = " +  ComplexMatrix4.dirac5)
  }

}
