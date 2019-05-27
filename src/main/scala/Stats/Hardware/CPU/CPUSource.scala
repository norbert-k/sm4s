package Stats.Hardware.CPU

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** CPUStats source for AKKA Streams
  */
class CPUSource()(implicit val system: SystemMetrics) extends DefaultSource[CPUStats] {
  override val outletName: String = "CPUStatsSource"

  val cpu = new CPU()

  override def getStatsAsync: Future[CPUStats] = {
    cpu.getCPUStatsAsync
  }
}

/** CPUStatsDynamic source for AKKA Streams
  */
class CPUDynamicSource()(implicit val system: SystemMetrics) extends DefaultSource[CPUStatsDynamic] {
  override val outletName: String = "CPUDynamicStatsSource"

  val cpu = new CPU()

  override def getStatsAsync: Future[CPUStatsDynamic] = {
    cpu.getCPUStatsDynamicAsync
  }
}
