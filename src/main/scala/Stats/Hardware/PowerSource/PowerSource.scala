package Stats.Hardware.PowerSource

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class PowerSourceStats(name: String, remainingCapacity: Double, timeRemaining: Double)

class PowerSource()(implicit val system: System) {
  def getPowerSourceStatsAsync: Future[Array[PowerSourceStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getPowerSourceStats
      }
    }
  }

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
