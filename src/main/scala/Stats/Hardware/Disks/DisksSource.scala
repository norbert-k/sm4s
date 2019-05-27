package Stats.Hardware.Disks

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** HWDiskStoreStats source for AKKA Streams
  */
class DisksSource()(implicit val system: SystemMetrics) extends DefaultSource[Array[HWDiskStoreStats]] {
  override val outletName: String = "DiskStoreStatsSource"

  val disks = new Disks()

  override def getStatsAsync: Future[Array[HWDiskStoreStats]] = {
    disks.getDiskStatsAsync
  }
}
