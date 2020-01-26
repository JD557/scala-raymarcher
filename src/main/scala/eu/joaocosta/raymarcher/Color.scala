package eu.joaocosta.raymarcher

case class Color(r: Int, g: Int, b: Int) {
  lazy val toMinartColor =
    eu.joaocosta.minart.Color(r, g, b)
}
