package Stats.Hardware.Network

import java.net.NetworkInterface

import Stats.SystemMetrics

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Network interface stats & info
  *
  * @constructor create new NetworkStats
  * @param bytesRecv        The Bytes Received.
  * @param bytesSent        The Bytes Sent.
  * @param displayName      The description of the network interface. On some platforms, this is identical to the name.
  * @param inErrors         Input Errors.
  * @param ipv4Addr         The IPv4 Addresses.
  * @param ipv6Addr         The IPv6 Addresses.
  * @param macAddr          The MAC Address.
  * @param mtu              The MTU of the network interface.
  * @param name             The interface name.
  * @param networkInterface The network interface.
  * @param outErrors        The Output Errors.
  * @param packetsRecv      The Packets Received.
  * @param packetsSent      The Packets Sent.
  * @param speed            The speed of the network interface in bits per second.
  * @param timestamp        Timestamp
  *
  */
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

class Network()(implicit val system: SystemMetrics) {
  /** Get NetworkStats asynchronously
    */
  def getNetworkStatsAsync: Future[Array[NetworkStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getNetworkStats
      }
    }
  }

  /** Get NetworkStats synchronously
    */
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
