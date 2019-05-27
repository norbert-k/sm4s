package Stats.Hardware.Baseboard

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class BaseboardSource()(implicit val system: System) extends DefaultSource[BaseboardStats] {
  override val outletName: String = "BaseboardStatsSource"

  val baseboard = new Baseboard()

  override def getStatsAsync: Future[BaseboardStats] = {
    baseboard.getBaseBoardStatsAsync
  }
}
