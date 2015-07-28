package eu.joaocosta.raymarcher

import eu.joaocosta.raymarcher.Objects.DistanceField
import eu.joaocosta.raymarcher.RayMarcher.DistanceSample
import org.apache.commons.math3.util.FastMath.{ sin, cos }

object Transformations {

  def translate(dx: Double, dy: Double, dz: Double)(obj: DistanceField): DistanceField = (v: Vec3d) => obj(v - (dx, dy, dz))

  def rotateX(theta: Double)(obj: DistanceField): DistanceField = {
    val s = sin(-theta)
    val c = cos(-theta)
    (v: Vec3d) => obj(v.rotX(s,c))
  }
  def rotateY(theta: Double)(obj: DistanceField): DistanceField = {
    val s = sin(-theta)
    val c = cos(-theta)
    (v: Vec3d) => obj(v.rotY(s,c))
  }
  def rotateZ(theta: Double)(obj: DistanceField): DistanceField = {
    val s = sin(-theta)
    val c = cos(-theta)
    (v: Vec3d) => obj(v.rotZ(s,c))
  }

  def scale(s: Double)(obj: DistanceField): DistanceField = (v: Vec3d) =>
    obj(v / s) match {
      case DistanceSample(dist, color) => DistanceSample(dist*s, color)
    }

  def repeat(dist: Double)(obj: DistanceField): DistanceField = {
    (v: Vec3d) => obj(new Vec3d(v.x % dist - dist/2, v.y % dist - dist/2, v.z % dist - dist/2))
  }

  def twistX(theta: Double)(obj: DistanceField): DistanceField = {
    (v: Vec3d) => obj(v.rotX(-theta * v.x))
  }

  def twistY(theta: Double)(obj: DistanceField): DistanceField = {
    (v: Vec3d) => obj(v.rotY(-theta * v.y))
  }

  def twistZ(theta: Double)(obj: DistanceField): DistanceField = {
    (v: Vec3d) => obj(v.rotZ(-theta * v.z))
  }

}
