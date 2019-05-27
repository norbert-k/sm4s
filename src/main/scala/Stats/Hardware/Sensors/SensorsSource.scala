package Stats.Hardware.Sensors

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** SensorStats source for AKKA Streams
  */
class SensorsSource()(implicit val system: SystemMetrics) extends DefaultSource[SensorStats] {
  override val outletName: String = "SensorStatsSource"

  val sensors = new Sensors()

  override def getStatsAsync: Future[SensorStats] = {
    sensors.getSensorStatsAsync
  }
}
