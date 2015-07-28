package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.RayMarcher
import scala.annotation.tailrec

abstract class Scene(width: Int, height: Int, scale: Int = 1) {

  val rayMarcher = new RayMarcher(width, height, scale)

  val startTime = System.currentTimeMillis()
  var frame = 0

  def render(rayMarcher: RayMarcher, t: Double): Unit

  @tailrec
  final def run(): Unit = {
    val t = (System.currentTimeMillis() - startTime)/1000.0
    render(rayMarcher, t)
    frame = frame + 1
    if (frame % 30 == 0) println("FPS:" + frame/t)
    run()
  }


}
