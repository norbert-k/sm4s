package Stats.Runtime

import java.lang

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Runtime (JVM) stats
  *
  * @constructor create new RuntimeStats
  * @param maxMemory           Max memory (XMX)
  * @param freeMemory          Free memory
  * @param availableProcessors Available processors
  * @param usedMemory          Used memory
  *
  */
case class RuntimeStats(maxMemory: Long, freeMemory: Long, availableProcessors: Int, usedMemory: Long)

class Runtime()(implicit val system: System) {
  /** Get RuntimeStats asynchronously
    */
  def getRuntimeStatsAsync: Future[RuntimeStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getRuntimeStats
      }
    }
  }

  /** Get RuntimeStats synchronously
    */
  def getRuntimeStats: RuntimeStats = {
    val rt: lang.Runtime = Runtime.getRuntime
    RuntimeStats(
      maxMemory = rt.maxMemory(),
      freeMemory = rt.freeMemory(),
      usedMemory = rt.maxMemory() - rt.freeMemory(),
      availableProcessors = rt.availableProcessors()
    )
  }
}
