package eu.joaocosta.raymarcher

import org.apache.commons.math3.util.FastMath.{ sin, cos, sqrt, max => fastMax, min => fastMin }

case class Vec3d(x: Double, y: Double, z: Double) {
  def +(that: Vec3d) = new Vec3d(this.x + that.x, this.y + that.y, this.z + that.z)
  def +(xx: Double, yy: Double, zz: Double) = new Vec3d(x + xx, y + yy, z + zz)
  def -(that: Vec3d) = new Vec3d(this.x - that.x, this.y - that.y, this.z - that.z)
  def -(xx: Double, yy: Double, zz: Double) = new Vec3d(x - xx, y - yy, z - zz)
  def *(that: Double) = new Vec3d(this.x * that, this.y * that, this.z * that)
  def /(that: Double) = new Vec3d(this.x / that, this.y / that, this.z / that)

  def dot(that: Vec3d) = this.x * that.x + this.y*that.y + this.z*that.z

  def rotX(theta: Double): Vec3d = rotX(sin(theta), cos(theta))
  def rotX(s: Double, c: Double): Vec3d = new Vec3d(x, c*y - s*z, s*y - c*z)
  def rotY(theta: Double): Vec3d = rotY(sin(theta), cos(theta))
  def rotY(s: Double, c: Double): Vec3d = new Vec3d(c*x - s*z, y, s*x + c*z)
  def rotZ(theta: Double): Vec3d = rotZ(sin(theta), cos(theta))
  def rotZ(s: Double, c: Double): Vec3d = new Vec3d(c*x + s*y, -s*x + c*y, z)

  def map(f: Double => Double) = new Vec3d(f(x), f(y), f(z))

  lazy val size = sqrt(x*x + y*y + z*z)
  lazy val normalized = new Vec3d(x/size, y/size, z/size)
  lazy val max = fastMax(x, fastMax(y, z))
  lazy val min = fastMin(x, fastMin(y, z))

  override lazy val toString = s"v($x,  $y, $z)"
}






