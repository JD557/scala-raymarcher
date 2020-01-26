package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.RayMarcher
import eu.joaocosta.raymarcher.Transformations._

object RotatingCylinder extends Scene(640,480) with App {

  val translation = translate(0, 0, 10) _
  val scaling = scale(0.1) _

  override def render(rayMarcher: RayMarcher, t: Double): Unit = {
    val rotation = rotateY(t * math.Pi / 8) _
     rayMarcher.renderScene(canvas, List(
       translation(rotation(scaling(cylinder)))
     ))
  }

  run()
}
