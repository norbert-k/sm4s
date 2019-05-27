package Stats.Hardware.Memory

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class MemoryStats(available: Long, total: Long, used: Long)

case class SwapStats(swapPagesIn: Long, swapPagesOut: Long, total: Long, used: Long, available: Long)

class Memory()(implicit val system: System) {
  def getMemoryStatsAsync: Future[MemoryStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getMemoryStats
      }
    }
  }

  def getMemoryStats: MemoryStats = {
    val memory = system.getSystemInfo.getHardware.getMemory
    MemoryStats(
      available = memory.getAvailable,
      total = memory.getTotal,
      used = memory.getTotal - memory.getAvailable
    )
  }

  def getSwapStatsAsync: Future[SwapStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getSwapStats
      }
    }
  }

  def getSwapStats: SwapStats = {
    val memory = system.getSystemInfo.getHardware.getMemory
    SwapStats(
      swapPagesIn = memory.getSwapPagesIn,
      swapPagesOut = memory.getSwapPagesOut,
      total = memory.getSwapTotal,
      used = memory.getSwapUsed,
      available = memory.getSwapTotal - memory.getSwapUsed
    )
  }
}
