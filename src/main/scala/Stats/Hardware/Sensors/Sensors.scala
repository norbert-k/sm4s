package Stats.Hardware.Sensors

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class SensorStats(cpuTemperature: Double, cpuVoltage: Double, fanSpeeds: Array[Int])

class Sensors()(implicit val system: System) {
  def getSensorStats: SensorStats = {
    var sensors = system.getSystemInfo.getHardware.getSensors
    SensorStats(
      cpuTemperature = sensors.getCpuTemperature,
      cpuVoltage = sensors.getCpuVoltage,
      fanSpeeds = sensors.getFanSpeeds
    )
  }

  def getSensorStatsAsync: Future[SensorStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getSensorStats
      }
    }
  }
}
