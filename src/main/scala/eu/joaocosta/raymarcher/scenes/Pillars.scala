package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.RayMarcher
import eu.joaocosta.raymarcher.Transformations._

object Pillars extends Scene(640, 480, 1) with App {

  val pillar = rotateX(math.Pi / 2.0)(scale(0.5)(cylinder))

  override def render(rayMarcher: RayMarcher, t: Double): Unit =
    rayMarcher.renderScene(List(
      translate( 0, 7, 10)(scale(5.0)(cube)),
      translate(-3, 0,  7)(pillar),
      translate( 3, 0,  7)(pillar),
      translate( 3, 0, 13)(pillar),
      translate(-3, 0, 13)(pillar),
      translate( 0, 1, 10)(sphere)
    ))

  run()
}
