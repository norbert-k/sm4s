package Stats.Hardware.Firmware

import Stats.SystemMetrics

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Firmware stats & info
  *
  * @constructor create new FirmwareStats
  * @param description  Firmware description.
  * @param manufacturer Firmware manufacturer.
  * @param name         Firmware name
  * @param releaseDate  Firmware release date in ISO 8601 YYYY-MM-DD format.
  * @param version      Firmware version
  *
  */
case class FirmwareStats(description: String, manufacturer: String, name: String, releaseDate: String, version: String)

class Firmware()(implicit val system: SystemMetrics) {
  /** Get FirmwareStats asynchronously
    */
  def getFirmwareStatsAsync: Future[FirmwareStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getFirmwareStats
      }
    }
  }

  /** Get FirmwareStats synchronously
    */
  def getFirmwareStats: FirmwareStats = {
    val firmware = system.getSystemInfo.getHardware.getComputerSystem.getFirmware
    FirmwareStats(
      description = firmware.getDescription,
      manufacturer = firmware.getManufacturer,
      name = firmware.getName,
      releaseDate = firmware.getReleaseDate,
      version = firmware.getVersion
    )
  }
}
