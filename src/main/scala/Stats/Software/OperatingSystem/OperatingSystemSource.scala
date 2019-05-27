package Stats.Software.OperatingSystem

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class OperatingSystemSource()(implicit val system: System) extends DefaultSource[OperatingSystemStats] {
  override val outletName: String = "OsProcessStatsSource"

  val os = new OperatingSystem()

  override def getStatsAsync: Future[OperatingSystemStats] = {
    os.getOperatingSystemStatsAsync
  }
}
