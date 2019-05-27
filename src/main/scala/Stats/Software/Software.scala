package Stats.Software
import Stats.Software.OSProcess.OsProcess
import Stats.System
import Stats.Software.OperatingSystem.OperatingSystem

class Software()(implicit val system: System) {
  def getOperatingSystem = new OperatingSystem()
  def getOsProcess = new OsProcess()
}
