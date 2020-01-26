package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.Lighting._
import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.RayMarcher
import eu.joaocosta.raymarcher.Transformations._

object RotatingCube extends Scene(640,480) with App {

  val translation = translate(0, 0, 10) _
  val scaling = scale(2.0) _

  override def render(rayMarcher: RayMarcher, t: Double): Unit = {
    val rotation = twistY(t * math.Pi / 16) _
    rayMarcher.renderScene(canvas, List(
      translation(scaling(rotation(cube)))
    ), lightingModel = phong(0.02, ambientOccl = false))
  }

  run()
}
