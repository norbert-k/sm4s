package Stats

import Stats.Hardware.Hardware
import Stats.Runtime.Runtime
import Stats.Software.Software

class Stats()(implicit val system: SystemMetrics) {
  /** Get Hardware object
    */
  def getHardware = new Hardware()

  /** Get Software object
    */
  def getSoftware = new Software()

  /** Get Runtime object
    */
  def getRuntime = new Runtime()
}
