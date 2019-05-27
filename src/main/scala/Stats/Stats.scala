package Stats

import Stats.Hardware.Hardware
import Stats.Software.Software

class Stats()(implicit val system: System) {
  def getHardware = new Hardware()
  def getSoftware = new Software()
}
