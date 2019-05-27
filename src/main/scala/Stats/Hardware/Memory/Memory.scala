package Stats.Hardware.Memory

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Memory (RAM) stats
  *
  * @constructor create new MemoryStats
  * @param available The amount of physical memory currently available, in bytes.
  * @param total     The amount of actual physical memory, in bytes.
  * @param used      The amount of physical memory currently in use, in bytes.
  *
  */
case class MemoryStats(available: Long, total: Long, used: Long)

/** Swap stats
  *
  * @constructor create new SwapStats
  * @param swapPagesIn  Number of pages read from paging/swap file(s) to resolve hard page faults. (Hard page faults occur when a process requires code or data that is not in its working set or elsewhere in physical memory, and must be retrieved from disk.) This property was designed as a primary indicator of the kinds of faults that cause system-wide delays. It includes pages retrieved to satisfy faults in the file system cache (usually requested by applications) and in non-cached mapped memory files.
  * @param swapPagesOut Number of pages written to paging/swap file(s) to free up space in physical memory. Pages are written back to disk only if they are changed in physical memory, so they are likely to hold data, not code. A high rate of pages output might indicate a memory shortage. The operating system writes more pages back to disk to free up space when physical memory is in short supply.
  * @param total        The current size of the paging/swap file(s), in bytes. If the paging/swap file can be extended, this is a soft limit.
  * @param used         The current memory committed to the paging/swap file(s), in bytes
  * @param available    The current memory available to the paging/swap file(s), in bytes
  * @param pageSize     The number of bytes in a memory page
  *
  */
case class SwapStats(swapPagesIn: Long, swapPagesOut: Long, total: Long, used: Long, available: Long, pageSize: Long)

class Memory()(implicit val system: System) {
  /** Get MemoryStats asynchronously
    */
  def getMemoryStatsAsync: Future[MemoryStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getMemoryStats
      }
    }
  }

  /** Get MemoryStats synchronously
    */
  def getMemoryStats: MemoryStats = {
    val memory = system.getSystemInfo.getHardware.getMemory
    MemoryStats(
      available = memory.getAvailable,
      total = memory.getTotal,
      used = memory.getTotal - memory.getAvailable
    )
  }

  /** Get SwapStats asynchronously
    */
  def getSwapStatsAsync: Future[SwapStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getSwapStats
      }
    }
  }

  /** Get SwapStats synchronously
    */
  def getSwapStats: SwapStats = {
    val memory = system.getSystemInfo.getHardware.getMemory
    SwapStats(
      swapPagesIn = memory.getSwapPagesIn,
      swapPagesOut = memory.getSwapPagesOut,
      total = memory.getSwapTotal,
      used = memory.getSwapUsed,
      available = memory.getSwapTotal - memory.getSwapUsed,
      pageSize = memory.getPageSize
    )
  }
}
