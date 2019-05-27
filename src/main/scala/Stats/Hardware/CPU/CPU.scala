package Stats.Hardware.CPU

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

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

case class CPULoadTicks(user: Long, nice: Long, system: Long, idle: Long, ioWait: Long, irq: Long, softIrq: Long, steal: Long) {
  def this(array: Array[Long]) = this(array(0), array(1), array(2), array(3), array(4), array(5), array(6), array(7))
}

case class CPUStatsDynamic(interrupts: Long,
                           cpuLoadBetweenTicks: Array[Double],
                           cpuLoadTicks: Array[CPULoadTicks],
                           systemCpuLoad: Double,
                           systemCpuLoadBetweenTicks: Double,
                           systemCpuLoadTicks: CPULoadTicks,
                           systemLoadAverage: Double,
                           systemUptime: Long)

class CPU()(implicit val system: System) {
  def getCPUStatsAsync: Future[CPUStats] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getCPUStats
      }
    }
  }

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

  def getCPUStatsDynamicAsync: Future[CPUStatsDynamic] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getCPUStatsDynamic
      }
    }
  }

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
