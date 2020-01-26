package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.{Color, RayMarcher}
import eu.joaocosta.raymarcher.Transformations._
import eu.joaocosta.raymarcher.Operations._
import eu.joaocosta.raymarcher.Textures._
import eu.joaocosta.raymarcher.Lighting._

object ShiftForward extends Scene(320, 240, 2) with App {

  val sfLogo =
    scale(0.5)(
      rotateZ(math.Pi / 4.0)(
        union(
          paint(Color(5, 184, 244))(cube),
          union(
            paint(Color(6, 208, 250))(translate(-2,0,0)(cube)),
            paint(Color(22, 116, 190))(translate(0,-2,0)(cube))
          )
        )
      )
  )

  val floor = paint(Color(128, 128, 128))(translate(0, 7, 10)(scale(5.0)(cube)))

  override def render(rayMarcher: RayMarcher, t: Double): Unit =
    rayMarcher.renderScene(canvas, List(
      paint(Color(128,128,128))(translate(0, 7, 10)(scale(5.0)(cube))),
      translate(0, 0, 10)(twistY(math.sin(t/4) * math.Pi / 4)(sfLogo))
    ), lightingModel = phong(0.02, ambientOccl = false))

  run()
}
