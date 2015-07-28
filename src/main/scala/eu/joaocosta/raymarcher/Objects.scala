package eu.joaocosta.raymarcher

import eu.joaocosta.raymarcher.RayMarcher.DistanceSample
import eu.joaocosta.raymarcher.Textures._
import org.apache.commons.math3.util.FastMath.{ min, max, abs }

object Objects {

  type DistanceField = (Vec3d => DistanceSample)

  private[this] def obj(distFun: Vec3d => Double, texture: Texture): DistanceField =
    (v: Vec3d) => {
      val d = distFun(v)
      DistanceSample(d, if (d>0) None else Some(texture(v)))
    }

  val empty: DistanceField = (v: Vec3d) => DistanceSample(Double.PositiveInfinity, None)

  val cube: DistanceField = obj({ v =>
      val di = v.map(abs(_) - 1)
      val dSide = di.max
      val dCorner = di.map(max(_, 0)).size
      min(dSide, dCorner)
    }, colorful)

  val sphere: DistanceField = obj(_.size - 1, striped)

  val cylinder: DistanceField = obj({v => new Vec3d(v.x, v.y, 0).size - 1}, striped)
}
