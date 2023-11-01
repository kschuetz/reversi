package software.kes.reversi

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class DummyTest extends AnyFunSpec with Matchers {
  describe("Dummy") {
    it("should return correct value") {
      Dummy.value shouldBe "Hello world!"
    }
  }
}
