package Stats.Hardware.Baseboard

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, _}

case class BaseboardStats(manufacturer: String, model: String, serialNumber: String, version: String)

class Baseboard()(implicit val system: System) {
  def getBaseboardStats: BaseboardStats = {
    BaseboardStats(
      manufacturer = system.getSystemInfo.getHardware.getComputerSystem.getBaseboard.getManufacturer,
      model = system.getSystemInfo.getHardware.getComputerSystem.getBaseboard.getModel,
      serialNumber = system.getSystemInfo.getHardware.getComputerSystem.getBaseboard.getSerialNumber,
      version = system.getSystemInfo.getHardware.getComputerSystem.getBaseboard.getVersion
    )
  }

  def getBaseBoardStatsAsync: Future[BaseboardStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getBaseboardStats
      }
    }
  }
}