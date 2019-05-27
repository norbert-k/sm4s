package Stats.Hardware.Sensors

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class SensorsSource()(implicit val system: System) extends DefaultSource[SensorStats] {
  override val outletName: String = "SensorStatsSource"

  val sensors = new Sensors()

  override def getStatsAsync: Future[SensorStats] = {
    sensors.getSensorStatsAsync
  }
}
