package Stats.Hardware.ComputerSystem

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class SystemStatsSource()(implicit val system: System) extends DefaultSource[ComputerSystemStats] {
  override val outletName: String = "BaseboardStatsSource"

  val cs = new ComputerSystem()

  override def getStatsAsync: Future[ComputerSystemStats] = {
    cs.getSystemStatsAsync
  }
}
