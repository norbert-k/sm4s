package Stats.Software.OperatingSystem

import Stats.SystemMetrics

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Operating system stats & info
  *
  * @constructor create new OperatingSystemStats
  * @param bitness      The number of bits supported by the operating system.
  * @param family       Operating system family.
  * @param manufacturer Operating system manufacturer.
  * @param processCount Get the number of processes currently running
  * @param version      Operating system version.
  *
  */
case class OperatingSystemStats(bitness: Int,
                                family: String,
                                manufacturer: String,
                                processCount: Int,
                                version: OperatingSystemVersion)

/** Operating system version
  *
  * @constructor create new OperatingSystemVersion
  * @param buildNumber Build number
  * @param codeName    Codename
  * @param version     Version
  *
  */
case class OperatingSystemVersion(buildNumber: String, codeName: String, version: String) {
  def this(operatingSystemVersion: oshi.software.os.OperatingSystemVersion) =
    this(operatingSystemVersion.getBuildNumber,
      operatingSystemVersion.getCodeName,
      operatingSystemVersion.getVersion)
}

class OperatingSystem()(implicit val system: SystemMetrics) {
  /** Get OperatingSystemStats asynchronously
    */
  def getOperatingSystemStatsAsync: Future[OperatingSystemStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getOperatingSystemStats
      }
    }
  }

  /** Get OperatingSystemStats synchronously
    */
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
