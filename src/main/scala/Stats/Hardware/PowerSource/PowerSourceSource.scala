package Stats.Hardware.PowerSource

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class PowerSourceSource()(implicit val system: System) extends DefaultSource[Array[PowerSourceStats]] {
  override val outletName: String = "PowerSourceStatsSource"

  val powerSource = new PowerSource()

  override def getStatsAsync: Future[Array[PowerSourceStats]] = {
    powerSource.getPowerSourceStatsAsync
  }
}
