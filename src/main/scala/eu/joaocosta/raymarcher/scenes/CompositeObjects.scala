package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.RayMarcher
import eu.joaocosta.raymarcher.Transformations._
import eu.joaocosta.raymarcher.Operations._

object CompositeObjects extends Scene(320, 240, 2) with App {

  val unionObject = union(
    cube,
    translate(0.5,-1,0)(sphere)
  )

  val intersectionObject = intersection(
    cube,
    translate(0.5,-1,0)(sphere)
  )

  val differenceObject = difference(
    cube,
    translate(0.5,-1,0)(sphere)
  )

  val translation = translate(0, 0, 10) _

  override def render(rayMarcher: RayMarcher, t: Double): Unit = {
    val rotation = rotateY(t * math.Pi / 8) _
    rayMarcher.renderScene(List(
      translate(-3,0,0)(translation(rotation(unionObject))),
      translate(0,0,0)(translation(rotation(intersectionObject))),
      translate(3,0,0)(translation(rotation(differenceObject)))
    ))
  }

  run()
}