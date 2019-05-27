package Stats.Software.OSProcess

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** OsProcessStats source for AKKA Streams
  */
class OsProcessSource()(implicit val system: SystemMetrics) extends DefaultSource[Array[OsProcessStats]] {
  override val outletName: String = "OsProcessStatsSource"

  val osProcess = new OsProcess()

  override def getStatsAsync: Future[Array[OsProcessStats]] = {
    osProcess.getOsProcessStatsAsync
  }
}
