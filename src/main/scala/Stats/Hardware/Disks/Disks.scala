package Stats.Hardware.Disks

import Stats.SystemMetrics

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Disk partition stats & info
  *
  * @constructor create new HWPartitionStats
  * @param major      Major device ID
  * @param minor      Minor device IC
  * @param mountPoint Mount point
  * @param name       The name
  * @param size       Size in bytes
  * @param _type      The type
  * @param uuid       The UUID
  *
  * */
case class HWPartitionStats(identification: String,
                            major: Int,
                            minor: Int,
                            mountPoint: String,
                            name: String,
                            size: Long,
                            _type: String,
                            uuid: String)

/** Disk stats & info
  *
  * @constructor create new HWDiskStoreStats
  * @param model              Disk model.
  * @param name               Disk name.
  * @param readBytes          The bytes read
  * @param reads              The reads
  * @param serial             The serial
  * @param size               Size of disk (in bytes)
  * @param timestamp          Timestamp
  * @param transferTime       The milliseconds spent reading or writing
  * @param writeBytes         The bytes written
  * @param writes             The writes
  * @param currentQueueLength The length of the disk queue (#I/O's in progress). Includes I/O requests that have been issued to the device driver but have not yet completed. Not supported on macOS.
  */
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
                            partitions: Array[HWPartitionStats],
                            currentQueueLength: Long)

class Disks()(implicit val system: SystemMetrics) {
  /** Get HWDiskStoreStats asynchronously
    */
  def getDiskStatsAsync: Future[Array[HWDiskStoreStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getDiskStats
      }
    }
  }

  /** Get HWDiskStoreStats synchronously
    */
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
            _type = partition.getType,
            partition.getUuid
          )
        },
        currentQueueLength = disk.getCurrentQueueLength
      )
    }
  }
}
