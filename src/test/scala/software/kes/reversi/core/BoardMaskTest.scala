package software.kes.reversi.core

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class BoardMaskTest extends AnyFunSpec with Matchers {
  describe("BoardMask") {
    it("should set correctly") {
      val base = BoardMask.empty
      (0 to 63).foreach { position =>
        val mask = base.set(position)
        mask.isSet(position) shouldBe true
        mask.isSet(position - 1) shouldBe false
        mask.isSet(position + 1) shouldBe false
      }
    }

    it("positions should work correctly") {
      val mask = BoardMask.positions(0, 1, 31, 32, 62, 63)

      mask.isSet(0) shouldBe true
      mask.isSet(1) shouldBe true
      mask.isSet(2) shouldBe false
      mask.isSet(30) shouldBe false
      mask.isSet(31) shouldBe true
      mask.isSet(32) shouldBe true
      mask.isSet(61) shouldBe false
      mask.isSet(62) shouldBe true
      mask.isSet(63) shouldBe true
    }
  }
}
