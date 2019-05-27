package Stats.Hardware.Firmware

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class FirmwareStats(description: String, manufacturer: String, name: String, releaseDate: String, version: String)

class Firmware()(implicit val system: System) {
  def getFirmwareStatsAsync: Future[FirmwareStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getFirmwareStats
      }
    }
  }

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
