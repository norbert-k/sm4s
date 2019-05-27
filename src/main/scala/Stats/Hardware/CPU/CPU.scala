package Stats.Hardware.CPU

import Stats.SystemMetrics

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** CPU (static) stats & info
  *
  * @constructor create a new ComputerSystemStats
  * @param identifier             Processor identifier.
  * @param logicalProcessorCount  The number of logical CPUs available.
  * @param model                  Processor model.
  * @param name                   Processor name.
  * @param physicalPackageCount   The number of physical packages available.
  * @param physicalProcessorCount The number of physical CPUs available.
  * @param processorId            A string representing the Processor ID
  * @param vendor                 Processor vendor.
  * @param vendorFrequency        Processor vendor frequency.
  * @param is64Bit                True if cpu is 64bit.
  */
case class CPUStats(family: String,
                    identifier: String,
                    logicalProcessorCount: Int,
                    model: String,
                    name: String,
                    physicalPackageCount: Int,
                    physicalProcessorCount: Int,
                    processorId: String,
                    vendor: String,
                    vendorFrequency: Long,
                    is64Bit: Boolean)

/** CPU Load ticks (CPU States)
  *
  * @constructor create new CPULoadTicks object
  * @param user    User
  * @param nice    Nice
  * @param system  System
  * @param idle    Idle
  * @param ioWait  IOWait
  * @param irq     IRQ
  * @param softIrq SoftIRQ
  * @param steal   Steal
  */
case class CPULoadTicks(user: Long, nice: Long, system: Long, idle: Long, ioWait: Long, irq: Long, softIrq: Long, steal: Long) {
  def this(array: Array[Long]) = this(array(0), array(1), array(2), array(3), array(4), array(5), array(6), array(7))
}

/** Dynamic (changing) CPU stats
  *
  * @constructor create a new ComputerSystemStats
  * @param interrupts                Number of interrupts which have occurred
  * @param cpuLoadBetweenTicks       Array of CPU load between 0 and 1 (100%) for each logical processor
  * @param cpuLoadTicks              A 2D array of logicalProcessorCount x 7 long values representing time spent in User, Nice, System, Idle, IOwait, IRQ, SoftIRQ, and Steal states.
  * @param systemCpuLoad             The "recent cpu usage" for the whole system; a negative value if not available.
  * @param systemCpuLoadBetweenTicks CPU load between 0 and 1 (100%)
  * @param systemCpuLoadTicks        An array of 7 long values representing time spent in User, Nice, System, Idle, IOwait, IRQ, SoftIRQ, and Steal states.
  * @param systemLoadAverage         The system load average; or a negative value if not available.
  * @param systemUptime              Number of seconds since boot.
  */
case class CPUStatsDynamic(interrupts: Long,
                           cpuLoadBetweenTicks: Array[Double],
                           cpuLoadTicks: Array[CPULoadTicks],
                           systemCpuLoad: Double,
                           systemCpuLoadBetweenTicks: Double,
                           systemCpuLoadTicks: CPULoadTicks,
                           systemLoadAverage: Double,
                           systemUptime: Long)

class CPU()(implicit val system: SystemMetrics) {
  /** Get CPUStats asynchronously
    */
  def getCPUStatsAsync: Future[CPUStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getCPUStats
      }
    }
  }

  /** Get CPUStats synchronously
    */
  def getCPUStats: CPUStats = {
    val processor = system.getSystemInfo.getHardware.getProcessor
    CPUStats(
      family = processor.getFamily,
      identifier = processor.getIdentifier,
      logicalProcessorCount = processor.getLogicalProcessorCount,
      model = processor.getModel,
      name = processor.getName,
      physicalPackageCount = processor.getPhysicalPackageCount,
      physicalProcessorCount = processor.getPhysicalProcessorCount,
      processorId = processor.getProcessorID,
      vendor = processor.getVendor,
      vendorFrequency = processor.getVendorFreq,
      is64Bit = processor.isCpu64bit
    )
  }

  /** Get CPUStatsDynamic asynchronously
    */
  def getCPUStatsDynamicAsync: Future[CPUStatsDynamic] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getCPUStatsDynamic
      }
    }
  }

  /** Get CPUStatsDynamic synchronously
    */
  def getCPUStatsDynamic: CPUStatsDynamic = {
    val processor = system.getSystemInfo.getHardware.getProcessor
    CPUStatsDynamic(
      interrupts = processor.getInterrupts,
      cpuLoadBetweenTicks = processor.getProcessorCpuLoadBetweenTicks,
      cpuLoadTicks = processor.getProcessorCpuLoadTicks.map(new CPULoadTicks(_)),
      systemCpuLoad = processor.getSystemCpuLoad,
      systemCpuLoadBetweenTicks = processor.getSystemCpuLoadBetweenTicks,
      systemCpuLoadTicks = new CPULoadTicks(processor.getSystemCpuLoadTicks),
      systemLoadAverage = processor.getSystemLoadAverage,
      systemUptime = processor.getSystemUptime
    )
  }
}
