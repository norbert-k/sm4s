package Stats

import Stats.Hardware.Hardware
import oshi.SystemInfo

class Stats()(implicit val system: System) {
  def getHardware = new Hardware()
}
