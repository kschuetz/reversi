package reversi.ui.models

object Point {
  val origin: Point = Point(0, 0)
}

case class Point(x: Double, y: Double) {
  def +(other: Point): Point = Point(x + other.x, y + other.y)

  def -(other: Point): Point = Point(x - other.x, y - other.y)

  def magnitude: Double = math.sqrt(x * x + y * y)

  def /(value: Double): Point = Point(x / value, y / value)

  def dot(other: Point): Double = x * other.x + y * other.y
}
