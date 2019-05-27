package Stats.Hardware.Baseboard

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, _}

/** Baseboard (Motherboard) stats & info
  *
  * @constructor create a new BaseboardStats
  * @param manufacturer The manufacturer.
  * @param model        The model.
  * @param serialNumber The version.
  * @param version      The serial number.
  */
case class BaseboardStats(manufacturer: String, model: String, serialNumber: String, version: String)

class Baseboard()(implicit val system: System) {
  /** Get BaseboardStats asynchronously
    */
  def getBaseBoardStatsAsync: Future[BaseboardStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getBaseboardStats
      }
    }
  }
  /** Get BaseboardStats synchronously
    */
  def getBaseboardStats: BaseboardStats = {
    BaseboardStats(
      manufacturer = system.getSystemInfo.getHardware.getComputerSystem.getBaseboard.getManufacturer,
      model = system.getSystemInfo.getHardware.getComputerSystem.getBaseboard.getModel,
      serialNumber = system.getSystemInfo.getHardware.getComputerSystem.getBaseboard.getSerialNumber,
      version = system.getSystemInfo.getHardware.getComputerSystem.getBaseboard.getVersion
    )
  }
}