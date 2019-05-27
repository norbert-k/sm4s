package Stats.Software

import Stats.Software.OSProcess.OsProcess
import Stats.Software.OperatingSystem.OperatingSystem
import Stats.SystemMetrics

class Software()(implicit val system: SystemMetrics) {
  /** Get OperatingSystem object
    */
  def getOperatingSystem = new OperatingSystem()

  /** Get OsProcess object
    */
  def getOsProcess = new OsProcess()
}
