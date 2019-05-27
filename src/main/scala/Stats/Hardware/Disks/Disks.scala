package Stats.Hardware.Disks

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class HWPartitionStats(identification: String,
                            major: Int,
                            minor: Int,
                            mountPoint: String,
                            name: String,
                            size: Long,
                            pType: String,
                            uuid: String)

case class HWDiskStoreStats(model: String,
                            name: String,
                            readBytes: Long,
                            reads: Long,
                            serial: String,
                            size: Long,
                            timestamp: Long,
                            transferTime: Long,
                            writeBytes: Long,
                            writes: Long,
                            partitions: Array[HWPartitionStats])

class Disks()(implicit val system: System) {
  def getDiskStatsAsync: Future[Array[HWDiskStoreStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getDiskStats
      }
    }
  }

  def getDiskStats: Array[HWDiskStoreStats] = {
    val hwDiskStore = system.getSystemInfo.getHardware.getDiskStores
    hwDiskStore.map { disk =>
      disk.updateDiskStats()
      HWDiskStoreStats(
        model = disk.getModel,
        name = disk.getName,
        readBytes = disk.getReadBytes,
        reads = disk.getReads,
        serial = disk.getSerial,
        size = disk.getSize,
        timestamp = disk.getTimeStamp,
        transferTime = disk.getTransferTime,
        writeBytes = disk.getWriteBytes,
        writes = disk.getWrites,
        partitions = disk.getPartitions.map { partition =>
          HWPartitionStats(
            identification = partition.getIdentification,
            major = partition.getMajor,
            minor = partition.getMinor,
            mountPoint = partition.getMountPoint,
            name = partition.getName,
            size = partition.getSize,
            pType = partition.getType,
            partition.getUuid
          )
        }
      )
    }
  }
}
