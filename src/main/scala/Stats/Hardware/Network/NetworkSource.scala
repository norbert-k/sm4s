package Stats.Hardware.Network

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** NetworkStats source for AKKA Streams
  */
class NetworkSource()(implicit val system: SystemMetrics) extends DefaultSource[Array[NetworkStats]] {
  override val outletName: String = "NetworkStatsSource"

  val network = new Network()

  override def getStatsAsync: Future[Array[NetworkStats]] = {
    network.getNetworkStatsAsync
  }
}
