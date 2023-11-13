package reversi.core

final class Engine(engineApi: EngineApi) {
  def setSeed(seed: Seed): Unit = {
    engineApi.setSeed(seed.lo, seed.hi)
  }

  def getSeed: Seed = {
    Seed(engineApi.getSeedLo(), engineApi.getSeedHi())
  }

  def generateRandomInt(bound: Int): Int =
    engineApi.generateRandomInt(bound)
}
