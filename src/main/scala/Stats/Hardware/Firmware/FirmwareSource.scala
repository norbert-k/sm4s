package Stats.Hardware.Firmware

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** FirmwareStats source for AKKA Streams
  */
class FirmwareSource()(implicit val system: SystemMetrics) extends DefaultSource[FirmwareStats] {
  override val outletName: String = "FirmwareStatsSource"

  val firmware = new Firmware()

  override def getStatsAsync: Future[FirmwareStats] = {
    firmware.getFirmwareStatsAsync
  }
}
