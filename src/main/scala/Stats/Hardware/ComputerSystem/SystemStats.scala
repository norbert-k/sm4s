package Stats.Hardware.ComputerSystem

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** System stats & info
  *
  * @constructor create a new ComputerSystemStats
  * @param manufacturer The manufacturer.
  * @param model        The model.
  * @param serialNumber The version.
  */
case class ComputerSystemStats(manufacturer: String, model: String, serialNumber: String)


class ComputerSystem()(implicit val system: System) {
  /** Get ComputerSystemStats asynchronously
    */
  def getSystemStatsAsync: Future[ComputerSystemStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getSystemStats
      }
    }
  }

  /** Get ComputerSystemStats synchronously
    */
  def getSystemStats: ComputerSystemStats = {
    val computerSystem = system.getSystemInfo.getHardware.getComputerSystem
    ComputerSystemStats(
      manufacturer = computerSystem.getManufacturer,
      model = computerSystem.getModel,
      serialNumber = computerSystem.getSerialNumber
    )
  }
}
