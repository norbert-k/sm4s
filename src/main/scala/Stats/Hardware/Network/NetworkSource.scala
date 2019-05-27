package Stats.Hardware.Network

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class NetworkSource()(implicit val system: System) extends DefaultSource[Array[NetworkStats]] {
  override val outletName: String = "NetworkStatsSource"

  val network = new Network()

  override def getStatsAsync: Future[Array[NetworkStats]] = {
    network.getNetworkStatsAsync
  }
}
