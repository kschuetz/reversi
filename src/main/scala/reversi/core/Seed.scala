package reversi.core

opaque type Seed = Long

object Seed {
  def apply(value: Long): Seed = value

  inline def apply(lo: Int, hi: Int): Seed =
    (hi.toLong << 32) | lo;

  extension (self: Seed) {
    def asLong: Long = self

    inline def lo: Int = (asLong & 0xFFFFFFFFL).toInt

    inline def hi: Int = (asLong >>> 32).toInt
  }
}
