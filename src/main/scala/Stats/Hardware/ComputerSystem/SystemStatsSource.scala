package Stats.Hardware.ComputerSystem

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** ComputerSystemStats source for AKKA Streams
  */
class SystemStatsSource()(implicit val system: SystemMetrics) extends DefaultSource[ComputerSystemStats] {
  override val outletName: String = "BaseboardStatsSource"

  val cs = new ComputerSystem()

  override def getStatsAsync: Future[ComputerSystemStats] = {
    cs.getSystemStatsAsync
  }
}
