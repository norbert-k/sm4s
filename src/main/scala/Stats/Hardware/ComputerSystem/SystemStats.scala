package Stats.Hardware.ComputerSystem

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class ComputerSystemStats(manufacturer: String, model: String, serialNumber: String)


class ComputerSystem()(implicit val system: System) {
  def getSystemStatsAsync: Future[ComputerSystemStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getSystemStats
      }
    }
  }

  def getSystemStats: ComputerSystemStats = {
    val computerSystem = system.getSystemInfo.getHardware.getComputerSystem
    ComputerSystemStats(
      manufacturer = computerSystem.getManufacturer,
      model = computerSystem.getModel,
      serialNumber = computerSystem.getSerialNumber
    )
  }
}
