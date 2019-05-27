package Stats.Hardware.Sensors

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Sensor stats
  *
  * @constructor create new SensorStats
  * @param cpuTemperature CPU Temperature in degrees Celsius if available, 0 otherwise.
  * @param cpuVoltage     CPU Voltage in Volts if available, 0 otherwise.
  * @param fanSpeeds      Speed in rpm for all fans. May return empty array if no fans detected or 0 fan speed if unable to measure fan speed.
  *
  */
case class SensorStats(cpuTemperature: Double, cpuVoltage: Double, fanSpeeds: Array[Int])

class Sensors()(implicit val system: System) {
  /** Get SensorStats asynchronously
    */
  def getSensorStatsAsync: Future[SensorStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getSensorStats
      }
    }
  }

  /** Get SensorStats synchronously
    */
  def getSensorStats: SensorStats = {
    var sensors = system.getSystemInfo.getHardware.getSensors
    SensorStats(
      cpuTemperature = sensors.getCpuTemperature,
      cpuVoltage = sensors.getCpuVoltage,
      fanSpeeds = sensors.getFanSpeeds
    )
  }
}
