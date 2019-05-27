package Stats.Hardware.UsbDevice

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class UsbDeviceSource()(implicit val system: System) extends DefaultSource[Array[UsbDeviceStats]] {
  override val outletName: String = "UsbStatsSource"

  val usb = new UsbDevice()

  override def getStatsAsync: Future[Array[UsbDeviceStats]] = {
    usb.getUsbDeviceStatsAsync
  }
}