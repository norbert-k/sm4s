package Stats.Hardware.Display

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

class DisplaySource()(implicit val system: SystemMetrics) extends DefaultSource[Array[DisplayStats]] {
  override val outletName: String = "DisplayStatsSource"

  val display = new Display()

  override def getStatsAsync: Future[Array[DisplayStats]] = {
    display.getDisplayStatsAsync
  }
}

