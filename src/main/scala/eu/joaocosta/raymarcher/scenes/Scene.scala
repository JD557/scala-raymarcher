package eu.joaocosta.raymarcher.scenes

import scala.annotation.tailrec
import scala.concurrent.duration._

import eu.joaocosta.minart.{AwtCanvas, JavaRenderLoop}

import eu.joaocosta.raymarcher.RayMarcher

abstract class Scene(width: Int, height: Int, scale: Int = 1) {

  val canvas = new AwtCanvas(width, height, scale)

  val rayMarcher = new RayMarcher(width, height)

  val startTime = System.currentTimeMillis()
  var frame = 0

  def render(rayMarcher: RayMarcher, t: Double): Unit

  final def run(): Unit = {
    JavaRenderLoop.infiniteRenderLoop(_ => {
      val t = (System.currentTimeMillis() - startTime)/1000.0
      render(rayMarcher, t)
      frame = frame + 1
      if (frame % 30 == 0) println("FPS:" + frame/t)
    }, 16.millis)
  }
}
