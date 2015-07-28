package eu.joaocosta.raymarcher

import eu.joaocosta.raymarcher.Objects.DistanceField
import eu.joaocosta.raymarcher.RayMarcher.DistanceSample
import org.apache.commons.math3.util.FastMath.{ min, max }

object Operations {
  def union(obj1: DistanceField, obj2: DistanceField): DistanceField = { (v: Vec3d) =>
    val o1 = obj1(v)
    val o2 = obj2(v)
    DistanceSample(min(o1.dist, o2.dist), o1.color.orElse(o2.color))
  }

  def intersection(obj1: DistanceField, obj2: DistanceField): DistanceField = { (v: Vec3d) =>
    val o1 = obj1(v)
    val o2 = obj2(v)
    val color = o1.color.flatMap{ x => o2.color }
    DistanceSample(max(o1.dist, o2.dist), color)
  }

  def difference(obj1: DistanceField, obj2: DistanceField): DistanceField = { (v: Vec3d) =>
    val o1 = obj1(v)
    val o2 = obj2(v)
    val color = (o1.color, o2.color) match {
      case (_, Some(_)) => None
      case _ => o1.color
    }
    DistanceSample(max(o1.dist, -o2.dist), color)
  }
}
