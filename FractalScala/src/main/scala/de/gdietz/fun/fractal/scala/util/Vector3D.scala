package de.gdietz.fun.fractal.scala.util

import de.gdietz.fun.fractal.util.{Coordinate3D, Vector3D => JavaVector3D}

import scala.language.implicitConversions

case class Vector3D(x: Double, y: Double, z: Double)
  extends Vector[Vector3D] {

  override def isZero: Boolean = x == 0.0 && y == 0.0 && z == 0.0

  override def normSqr: Double = x * x + y * y + z * z

  override def unary_- : Vector3D = Vector3D(-x, -y, -z)

  override def +(c: Vector3D): Vector3D = Vector3D(x + c.x, y + c.y, z + c.z)

  override def -(c: Vector3D): Vector3D = Vector3D(x - c.x, y - c.y, z - c.z)

  override def *(r: Double): Vector3D = Vector3D(r * x, r * y, r * z)

  override def /(r: Double): Vector3D = Vector3D(x / r, y / r, z / r)


  override val zero: Vector3D = Vector3D.zero


  def toQuaternion: Quaternion = Quaternion(x, y, z)

  def toImagQuaternion: Quaternion = Quaternion(0.0, x, y, z)

  def toRealVector: RealVector3 =
    RealVector3(Real(x), Real(y), Real(y))

  def toJavaVector3D: JavaVector3D = new JavaVector3D(x, y, z)


  override def toString: String = "(" + x + ", " + y + ", " + z + ")"

}

object Vector3D {

  val zero: Vector3D = Vector3D(0.0, 0.0, 0.0)


  implicit def coordinate3DToVector3D(c: Coordinate3D): Vector3D =
    Vector3D(c.getX, c.getY, c.getZ)

}
