package Stats.Hardware

import Stats.Hardware.Baseboard.Baseboard
import Stats.Hardware.CPU.CPU
import Stats.Hardware.Disks.Disks
import Stats.Hardware.Display.Display
import Stats.Hardware.Firmware.Firmware
import Stats.Hardware.Memory.Memory
import Stats.Hardware.Network.Network
import Stats.Hardware.PowerSource.PowerSource
import Stats.Hardware.Sensors.Sensors
import Stats.Hardware.SoundCard.SoundCard
import Stats.Hardware.UsbDevice.UsbDevice
import Stats.SystemMetrics

class Hardware()(implicit val system: SystemMetrics) {
  /** Get Baseboard object
    */
  def getBaseboard = new Baseboard()

  /** Get CPU object
    */
  def getCPU = new CPU()

  /** Get Disks object
    */
  def getDisks = new Disks()

  /** Get Display object
    */
  def getDisplay = new Display()

  /** Get Firmware object
    */
  def getFirmware = new Firmware()

  /** Get Memory object
    */
  def getMemory = new Memory()

  /** Get Network object
    */
  def getNetwork = new Network()

  /** Get PowerSource object
    */
  def getPowerSource = new PowerSource()

  /** Get Sensors object
    */
  def getSensors = new Sensors()

  /** Get SoundCard object
    */
  def getSoundCards = new SoundCard()

  /** Get UsbDevice object
    */
  def getUsbDevices = new UsbDevice()

}
