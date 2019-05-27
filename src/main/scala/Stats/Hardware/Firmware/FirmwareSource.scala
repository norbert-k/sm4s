package Stats.Hardware.Firmware

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class FirmwareSource()(implicit val system: System) extends DefaultSource[FirmwareStats] {
  override val outletName: String = "FirmwareStatsSource"

  val firmware = new Firmware()

  override def getStatsAsync: Future[FirmwareStats] = {
    firmware.getFirmwareStatsAsync
  }
}
