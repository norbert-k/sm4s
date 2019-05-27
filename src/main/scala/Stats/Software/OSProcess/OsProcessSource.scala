package Stats.Software.OSProcess

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class OsProcessSource()(implicit val system: System) extends DefaultSource[Array[OsProcessStats]] {
  override val outletName: String = "OsProcessStatsSource"

  val osProcess = new OsProcess()

  override def getStatsAsync: Future[Array[OsProcessStats]] = {
    osProcess.getOsProcessStatsAsync
  }
}
