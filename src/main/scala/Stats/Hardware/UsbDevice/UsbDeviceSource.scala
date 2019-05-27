package Stats.Hardware.UsbDevice

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** UsbDeviceStats source for AKKA Streams
  */
class UsbDeviceSource()(implicit val system: SystemMetrics) extends DefaultSource[Array[UsbDeviceStats]] {
  override val outletName: String = "UsbStatsSource"

  val usb = new UsbDevice()

  override def getStatsAsync: Future[Array[UsbDeviceStats]] = {
    usb.getUsbDeviceStatsAsync
  }
}