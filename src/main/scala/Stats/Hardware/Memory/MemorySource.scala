package Stats.Hardware.Memory

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class MemorySource()(implicit val system: System) extends DefaultSource[MemoryStats] {
  override val outletName: String = "MemoryStatsSource"

  val memory = new Memory()

  override def getStatsAsync: Future[MemoryStats] = {
    memory.getMemoryStatsAsync
  }
}

class SwapSource()(implicit val system: System) extends DefaultSource[SwapStats] {
  override val outletName: String = "SwapStatsSource"

  val memory = new Memory()

  override def getStatsAsync: Future[SwapStats] = {
    memory.getSwapStatsAsync
  }
}