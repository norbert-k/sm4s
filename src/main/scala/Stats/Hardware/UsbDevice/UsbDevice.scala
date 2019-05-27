package Stats.Hardware.UsbDevice

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class UsbDeviceStats(name: String, productId: String, serialNumber: String, vendor: String, vendorId: String)

class UsbDevice()(implicit val system: System) {
  def getUsbDeviceStatsAsync: Future[Array[UsbDeviceStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getUsbDeviceStats
      }
    }
  }

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
