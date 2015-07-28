package eu.joaocosta.raymarcher.scenes

import eu.joaocosta.raymarcher.Objects._
import eu.joaocosta.raymarcher.RayMarcher
import eu.joaocosta.raymarcher.Transformations._

object RotatingCylinder extends App {

   val rayMarcher = new RayMarcher(640, 480)

   val startTime = System.currentTimeMillis()
   var frame = 0
   while(true) {
     val t = (System.currentTimeMillis() - startTime)/1000.0
     val translation = translate(0, 0, 10) _
     val rotation = rotateY(t * math.Pi / 8) _
     val scaling = scale(0.1) _
     rayMarcher.renderScene(List(
       translation(rotation(scaling(cylinder)))
       ))
     frame = frame + 1
     if (frame % 30 == 0) println("FPS:" + frame/t)
   }
 }