package eu.joaocosta.raymarcher

import eu.joaocosta.raymarcher.Lighting._
import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.RayMarcher._
import org.apache.commons.math3.util.FastMath._

import scala.annotation.tailrec

class RayMarcher(width: Int, height: Int, scale: Int = 1, zNear: Double = 1.0, zFar: Double = 30.0, minStep: Double = 0.02) {
  private[this] val ratio = height.toDouble / width
  private[this] val canvas = new GraphicalCanvas(width, height, scale)
  private[this] val pixelList = (0 until width).flatMap { x =>
    (0 until height).map { y =>
      (x, y, (x.toDouble - width/2)/width, ratio * (y.toDouble - height/2)/height)
    }
  }.toVector.par

  def renderScene(objs: Seq[DistanceField], light: Light = defaultLight, lightingModel: LightingModel = defaultPhong) = {
    val scene = objs.fold(empty){Operations.union}

    pixelList.foreach { case (x, y, xx, yy) =>
      canvas.putPixel(x, y,
        traceRay(xx, yy, scene, light, lightingModel, zNear, zFar, minStep)
      )
    }

    canvas.redraw()
  }
}

object RayMarcher {
  case class DistanceSample(dist: Double, color: Option[Color])

  val defaultLight = Light(new Vec3d(5, -5, 7), new Color(128,128,128))
  val defaultPhong = phong(0.02, ambientOccl = true, shadows = true)

  def traceRay(x: Double, y: Double, scene: DistanceField, light: Light, lightingModel: LightingModel, zNear: Double, zFar: Double, minStepSize: Double): Color = {
    val initVector = new Vec3d(x, y, zNear)
    val delta = initVector.normalized
    @tailrec
    def traceRayAux(pos: Vec3d): Color = scene(pos) match {
      case DistanceSample(_, Some(c)) =>
        lightingModel(pos, delta * -1, light, scene, c)
      case DistanceSample(_, None) if pos.z > zFar =>
        Color(0,0,0)
      case DistanceSample(d, None) =>
        traceRayAux(pos + delta * max(d,minStepSize))
    }
    traceRayAux(initVector)
  }
}
