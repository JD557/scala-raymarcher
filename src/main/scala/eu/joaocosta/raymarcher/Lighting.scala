package eu.joaocosta.raymarcher

import eu.joaocosta.raymarcher.Objects.DistanceField
import eu.joaocosta.raymarcher.RayMarcher.DistanceSample
import org.apache.commons.math3.util.FastMath._

import scala.annotation.tailrec

object Lighting {

  type LightingModel = (Vec3d, Vec3d, Light, DistanceField, Color) => Color
  case class Light(pos: Vec3d, color: Color)

  private[this] def normal(p: Vec3d, scene: DistanceField, epsilon: Double): Vec3d = {
    val dx = scene(p + (epsilon, 0, 0)).dist - scene(p - (epsilon, 0, 0)).dist
    val dy = scene(p + (0, epsilon, 0)).dist - scene(p - (0, epsilon, 0)).dist
    val dz = scene(p + (0, 0, epsilon)).dist - scene(p - (0, 0, epsilon)).dist
    new Vec3d(dx, dy, dz).normalized
  }

  private[this] def shadow(p: Vec3d, lPos: Vec3d, scene: DistanceField, epsilon: Double, k: Int) = {
    val l = lPos - p
    val initVector = l.normalized
    val maxSize = l.size
    @tailrec
    def shadowAux(delta: Vec3d, accum: Double): Double = {
      val size = delta.size
      if (size > maxSize) accum
      else {
        val dist = scene(p + delta).dist
        if (dist <= 0.0) 0.0
        else if (size * k < 1.0) shadowAux(delta + (initVector * max(dist, epsilon)), accum)
        else shadowAux(delta + (initVector * max(dist, epsilon)), min(accum, k * dist / size))
      }
    }
    shadowAux(initVector * epsilon, 1.0)
  }

  private[this] def ambientOcclusion(p: Vec3d, n: Vec3d, scene: DistanceField, epsilon: Double, iter: Int) = {
    val bias = scene(p).dist
    @tailrec
    def ambientOcclusionAux(i: Int, accum: Double, total: Double): Double = i match {
      case 0 => accum / total
      case i =>
        val expected = bias + (i * epsilon)
        val real = scene(p + n * (i * epsilon)).dist
        val decay = pow(2, i)
        ambientOcclusionAux(i-1, accum + (expected - real) / decay, total + expected / decay)
    }
    1 - ambientOcclusionAux(iter, 0, 0)
  }

  def phong(epsilon: Double, ambientOccl: Boolean, shadows: Boolean = true) = {

    val ambientFun =
      if (ambientOccl) ambientOcclusion _
      else (_: Vec3d, _: Vec3d, _: DistanceField, _: Double, _: Int) => 1.0

    val shadingFun =
      if (shadows) shadow _
      else (_: Vec3d, _: Vec3d, _: DistanceField, _: Double, _: Int) => 1.0

    (p: Vec3d, v: Vec3d, light: Light, scene: DistanceField, ambientColor: Color) => {
      val l = (light.pos - p).normalized
      val n = normal(p, scene, epsilon)
      val r = n.normalized * (2 * (l.normalized dot n.normalized)) - l.normalized
      val ambient = ambientFun(p, n.normalized, scene, epsilon, 5)
      val diffuse = max(0, n.normalized dot l.normalized)
      val specular = pow(max(0, r.normalized dot v.normalized), 2)
      val shading = shadingFun(p, light.pos, scene, 0.2, 32)
      Color(
        max(min((ambientColor.r * ambient + shading * (light.color.r * diffuse + light.color.r * specular)).toInt, 255), 0),
        max(min((ambientColor.g * ambient + shading * (light.color.g * diffuse + light.color.g * specular)).toInt, 255), 0),
        max(min((ambientColor.b * ambient + shading * (light.color.b * diffuse + light.color.b * specular)).toInt, 255), 0)
      )
    }
  }

}
