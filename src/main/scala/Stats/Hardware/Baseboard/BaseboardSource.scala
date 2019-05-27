package Stats.Hardware.Baseboard

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** BaseboardStats source for AKKA Streams
  */
class BaseboardSource()(implicit val system: SystemMetrics) extends DefaultSource[BaseboardStats] {
  override val outletName: String = "BaseboardStatsSource"

  val baseboard = new Baseboard()

  override def getStatsAsync: Future[BaseboardStats] = {
    baseboard.getBaseBoardStatsAsync
  }
}
