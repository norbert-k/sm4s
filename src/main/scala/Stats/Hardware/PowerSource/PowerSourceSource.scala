package Stats.Hardware.PowerSource

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** PowerSourceStats source for AKKA Streams
  */
class PowerSourceSource()(implicit val system: SystemMetrics) extends DefaultSource[Array[PowerSourceStats]] {
  override val outletName: String = "PowerSourceStatsSource"

  val powerSource = new PowerSource()

  override def getStatsAsync: Future[Array[PowerSourceStats]] = {
    powerSource.getPowerSourceStatsAsync
  }
}
