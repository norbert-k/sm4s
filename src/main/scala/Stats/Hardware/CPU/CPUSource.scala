package Stats.Hardware.CPU

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class CPUSource()(implicit val system: System) extends DefaultSource[CPUStats] {
  override val outletName: String = "CPUStatsSource"

  val cpu = new CPU()

  override def getStatsAsync: Future[CPUStats] = {
    cpu.getCPUStatsAsync
  }
}

class CPUDynamicSource()(implicit val system: System) extends DefaultSource[CPUStatsDynamic] {
  override val outletName: String = "CPUDynamicStatsSource"

  val cpu = new CPU()

  override def getStatsAsync: Future[CPUStatsDynamic] = {
    cpu.getCPUStatsDynamicAsync
  }
}
