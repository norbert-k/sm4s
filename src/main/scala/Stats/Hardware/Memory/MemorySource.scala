package Stats.Hardware.Memory

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** MemoryStats source for AKKA Streams
  */
class MemorySource()(implicit val system: SystemMetrics) extends DefaultSource[MemoryStats] {
  override val outletName: String = "MemoryStatsSource"

  val memory = new Memory()

  override def getStatsAsync: Future[MemoryStats] = {
    memory.getMemoryStatsAsync
  }
}

/** SwapStats source for AKKA Streams
  */
class SwapSource()(implicit val system: SystemMetrics) extends DefaultSource[SwapStats] {
  override val outletName: String = "SwapStatsSource"

  val memory = new Memory()

  override def getStatsAsync: Future[SwapStats] = {
    memory.getSwapStatsAsync
  }
}