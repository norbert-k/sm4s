package Stats.Software.OperatingSystem

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class OperatingSystemStats(bitness: Int,
                                family: String,
                                manufacturer: String,
                                processCount: Int,
                                version: OperatingSystemVersion)

case class OperatingSystemVersion(buildNumber: String, codeName: String, version: String) {
  def this(operatingSystemVersion: oshi.software.os.OperatingSystemVersion) =
    this(operatingSystemVersion.getBuildNumber,
      operatingSystemVersion.getCodeName,
      operatingSystemVersion.getVersion)
}

class OperatingSystem()(implicit val system: System) {
  def getOperatingSystemStatsAsync: Future[OperatingSystemStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getOperatingSystemStats
      }
    }
  }

  def getOperatingSystemStats: OperatingSystemStats = {
    val os = system.getSystemInfo.getOperatingSystem
    OperatingSystemStats(
      bitness = os.getBitness,
      family = os.getFamily,
      manufacturer = os.getManufacturer,
      processCount = os.getProcessCount,
      version = new OperatingSystemVersion(os.getVersion)
    )
  }
}
