package reversi.core

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import reversi.core.BoardMask

class BoardMaskTest extends AnyFunSpec with Matchers {
  describe("BoardMask") {
    it("should set correctly") {
      val base = BoardMask.empty
      SquareIndex.All.foreach { si =>
        val mask = base.set(si)
        mask.isSet(si) shouldBe true
        mask.isSet(si - 1) shouldBe false
        mask.isSet(si + 1) shouldBe false
      }
    }

    it("positions should work correctly") {
      val mask = BoardMask.squares(SquareIndex(0), SquareIndex(1), SquareIndex(31), SquareIndex(32),
        SquareIndex(62), SquareIndex(63))

      mask.isSet(SquareIndex(0)) shouldBe true
      mask.isSet(SquareIndex(1)) shouldBe true
      mask.isSet(SquareIndex(2)) shouldBe false
      mask.isSet(SquareIndex(30)) shouldBe false
      mask.isSet(SquareIndex(31)) shouldBe true
      mask.isSet(SquareIndex(32)) shouldBe true
      mask.isSet(SquareIndex(61)) shouldBe false
      mask.isSet(SquareIndex(62)) shouldBe true
      mask.isSet(SquareIndex(63)) shouldBe true
    }
  }
}
