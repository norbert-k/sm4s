package Stats.Hardware.Disks

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class DisksSource()(implicit val system: System) extends DefaultSource[Array[HWDiskStoreStats]] {
  override val outletName: String = "DiskStoreStatsSource"

  val disks = new Disks()

  override def getStatsAsync: Future[Array[HWDiskStoreStats]] = {
    disks.getDiskStatsAsync
  }
}
