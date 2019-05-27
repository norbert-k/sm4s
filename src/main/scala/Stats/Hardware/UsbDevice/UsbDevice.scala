package Stats.Hardware.UsbDevice

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** USB Device stats & info
  *
  * @constructor create new UsbDeviceStats
  * @param name         Name of the USB device
  * @param productId    Product ID of the USB device
  * @param serialNumber Serial number of the USB device
  * @param vendor       Name of the USB device
  * @param vendorId     ID of the vendor that manufactured the USB device
  *
  */
case class UsbDeviceStats(name: String, productId: String, serialNumber: String, vendor: String, vendorId: String)

class UsbDevice()(implicit val system: System) {
  /** Get UsbDeviceStats asynchronously
    */
  def getUsbDeviceStatsAsync: Future[Array[UsbDeviceStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getUsbDeviceStats
      }
    }
  }

  /** Get UsbDeviceStats synchronously
    */
  def getUsbDeviceStats: Array[UsbDeviceStats] = {
    val usbDevices = system.getSystemInfo.getHardware.getUsbDevices(false)
    usbDevices.map { usbDevice =>
      UsbDeviceStats(
        name = usbDevice.getName,
        productId = usbDevice.getProductId,
        serialNumber = usbDevice.getSerialNumber,
        vendor = usbDevice.getVendor,
        vendorId = usbDevice.getVendorId
      )
    }
  }
}
