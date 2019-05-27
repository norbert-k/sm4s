package Stats.Hardware.PowerSource

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** PowerSource stats & info
  *
  * @constructor create new PowerSourceStats
  * @param name              Name of the power source (e.g., InternalBattery-0)
  * @param remainingCapacity Remaining capacity as a fraction of max capacity.
  * @param timeRemaining     Estimated time remaining on the power source, in seconds.
  *
  */
case class PowerSourceStats(name: String, remainingCapacity: Double, timeRemaining: Double)

class PowerSource()(implicit val system: System) {
  /** Get PowerSourceStats asynchronously
    */
  def getPowerSourceStatsAsync: Future[Array[PowerSourceStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getPowerSourceStats
      }
    }
  }

  /** Get PowerSourceStats synchronously
    */
  def getPowerSourceStats: Array[PowerSourceStats] = {
    val powerSources = system.getSystemInfo.getHardware.getPowerSources
    powerSources.map { powerSource =>
      PowerSourceStats(
        name = powerSource.getName,
        remainingCapacity = powerSource.getRemainingCapacity,
        timeRemaining = powerSource.getTimeRemaining
      )
    }
  }
}
