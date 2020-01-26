package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.RayMarcher
import eu.joaocosta.raymarcher.Transformations._

object RotatingSphere extends Scene(640, 480) with App {

  val translation = translate(0, 0, 10) _

  override def render(rayMarcher: RayMarcher, t: Double): Unit = {
    val rotation = rotateY(t * math.Pi / 8) _
    rayMarcher.renderScene(canvas, List(
    translation(rotation(sphere))
    ))
  }

  run()

}
