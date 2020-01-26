package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.RayMarcher
import eu.joaocosta.raymarcher.Transformations._

object RepeatingCube extends Scene(640,480) with App {

  val translation = translate(0, 0, 20) _
  val repetition = repeat(3.0) _

  override def render(rayMarcher: RayMarcher, t: Double): Unit = {
    val rotation = rotateY(t * math.Pi / 8) _
    rayMarcher.renderScene(canvas, List(
      translation(rotation(repetition(cube)))
    ))
  }

  run()
}
