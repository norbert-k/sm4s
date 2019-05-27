package Stats.Runtime

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class RuntimeSource()(implicit val system: System) extends DefaultSource[RuntimeStats] {
  override val outletName: String = "RuntimeStatsSource"

  val runtime = new Runtime()

  override def getStatsAsync: Future[RuntimeStats] = {
    runtime.getRuntimeStatsAsync
  }
}
