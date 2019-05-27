package Stats.Software.OperatingSystem

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future
/** OperatingSystemStats source for AKKA Streams
  */
class OperatingSystemSource()(implicit val system: SystemMetrics) extends DefaultSource[OperatingSystemStats] {
  override val outletName: String = "OsProcessStatsSource"

  val os = new OperatingSystem()

  override def getStatsAsync: Future[OperatingSystemStats] = {
    os.getOperatingSystemStatsAsync
  }
}
