package eu.joaocosta.raymarcher

import java.awt.{Canvas, Graphics, Dimension}
import java.awt.image.{DataBufferInt, BufferedImage}
import javax.swing.JFrame

case class Color(r: Int, g: Int, b: Int)

class GraphicalCanvas(width: Int, height: Int, val scale: Int = 1) extends Canvas {

  val _width = width * scale
  val _height = height * scale
  val deltas = for {
    dx <- 0 until scale
    dy <- 0 until scale
  } yield (dx, dy)

  val frame = new JFrame()
  frame.setSize(new Dimension(_width, _height))
  frame.setMaximumSize(new Dimension(_width, _height))
  frame.setMinimumSize(new Dimension(_width, _height))
  frame.add(this)
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.pack()

  this.createBufferStrategy(2)
  val buffStrategy = getBufferStrategy
  val image = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_ARGB)
  val imagePixels = image.getRaster.getDataBuffer.asInstanceOf[DataBufferInt]

  private[this] def pack(r: Int, g: Int, b: Int) =
    (255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  private[this] def putPixelScaled(x: Int, y: Int, c: Color) = deltas.foreach { case (dx, dy) =>
    imagePixels.setElem((y * scale + dy) * _width + (x * scale + dx) % _width, pack(c.r, c.g, c.b))
  }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color) =
    imagePixels.setElem(y * _width + x % _width, pack(c.r, c.g, c.b))

  val putPixel = if (scale == 1) {(x: Int, y: Int, c: Color) => putPixelUnscaled(x, y, c)}
  else {(x: Int, y: Int, c: Color) => putPixelScaled(x, y, c)}


  def redraw(): Unit = {
    val g = buffStrategy.getDrawGraphics
    g.drawImage(image, 0, 0, _width, _height, this)
    g.dispose()
    buffStrategy.show()
  }

  override def repaint() = redraw()

  frame.setVisible(true)

}
