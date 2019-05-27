package Stats.Hardware.Network

import java.net.NetworkInterface

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class NetworkStats(bytesRecv: Long,
                        bytesSent: Long,
                        displayName: String,
                        inErrors: Long,
                        ipv4Addr: Array[String],
                        ipv6Addr: Array[String],
                        macAddr: String,
                        mtu: Int,
                        name: String,
                        networkInterface: NetworkInterface,
                        outErrors: Long,
                        packetsRecv: Long,
                        packetsSent: Long,
                        speed: Long,
                        timestamp: Long)

class Network()(implicit val system: System) {
  def getNetworkStatsAsync: Future[Array[NetworkStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getNetworkStats
      }
    }
  }

  def getNetworkStats: Array[NetworkStats] = {
    val networkInterfaces = system.getSystemInfo.getHardware.getNetworkIFs
    networkInterfaces.map { network =>
      network.updateNetworkStats()
      NetworkStats(
        bytesRecv = network.getBytesRecv,
        bytesSent = network.getBytesSent,
        displayName = network.getDisplayName,
        inErrors = network.getInErrors,
        ipv4Addr = network.getIPv4addr,
        ipv6Addr = network.getIPv6addr,
        macAddr = network.getMacaddr,
        mtu = network.getMTU,
        name = network.getName,
        networkInterface = network.getNetworkInterface,
        outErrors = network.getOutErrors,
        packetsRecv = network.getPacketsRecv,
        packetsSent = network.getPacketsSent,
        speed = network.getSpeed,
        timestamp = network.getTimeStamp
      )
    }
  }

}
