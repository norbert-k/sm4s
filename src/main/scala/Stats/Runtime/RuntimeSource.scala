package Stats.Runtime

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** RuntimeStats source for AKKA Streams
  */
class RuntimeSource()(implicit val system: SystemMetrics) extends DefaultSource[RuntimeStats] {
  override val outletName: String = "RuntimeStatsSource"

  val runtime = new Runtime()

  override def getStatsAsync: Future[RuntimeStats] = {
    runtime.getRuntimeStatsAsync
  }
}
