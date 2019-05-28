package Stats.Hardware.Display

import Stats.SystemMetrics
import oshi.util.EdidUtil

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Display stats & info
  *
  * @constructor create new HWPartitionStats
  * @param monitorHeight  Monitor height in cm
  * @param monitorWidth   Monitor width in cm
  * @param manufacturerId Manufacturer ID
  * @param productId      Product ID
  * @param serialNumber   Serial number
  * @param version        EDID version
  * @param week           Week of year of manufacture
  * @param year           Year of manufacture
  * @param isDigital      Test if this EDID is a digital monitor based on byte 20
  *
  * */
case class DisplayStats(monitorHeight: Int,
                        monitorWidth: Int,
                        manufacturerId: String,
                        productId: String,
                        serialNumber: String,
                        version: String,
                        week: Byte,
                        year: Int,
                        isDigital: Boolean)

class Display()(implicit val system: SystemMetrics) {
  def getDisplayStatsAsync: Future[Array[DisplayStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getDisplayStats
      }
    }
  }

  def getDisplayStats: Array[DisplayStats] = {
    system.getSystemInfo.getHardware.getDisplays.map { display =>
      display.getEdid
    }.map { displayEdid =>
      DisplayStats(
        monitorHeight = EdidUtil.getVcm(displayEdid),
        monitorWidth = EdidUtil.getHcm(displayEdid),
        manufacturerId = EdidUtil.getManufacturerID(displayEdid),
        productId = EdidUtil.getProductID(displayEdid),
        serialNumber = EdidUtil.getSerialNo(displayEdid),
        version = EdidUtil.getVersion(displayEdid),
        week = EdidUtil.getWeek(displayEdid),
        year = EdidUtil.getYear(displayEdid),
        isDigital = EdidUtil.isDigital(displayEdid)
      )
    }
  }

}