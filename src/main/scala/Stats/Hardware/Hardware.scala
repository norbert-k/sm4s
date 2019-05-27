package Stats.Hardware

import Stats.Hardware.Baseboard.Baseboard
import Stats.Hardware.CPU.CPU
import Stats.Hardware.Disks.Disks
import Stats.Hardware.Firmware.Firmware
import Stats.Hardware.Memory.Memory
import Stats.Hardware.Network.Network
import Stats.Hardware.PowerSource.PowerSource
import Stats.Hardware.Sensors.Sensors
import Stats.Hardware.SoundCard.SoundCard
import Stats.Hardware.UsbDevice.UsbDevice
import Stats.System

class Hardware()(implicit val system: System) {
  def getBaseboard = new Baseboard()
  def getCPU = new CPU()
  def getDisks = new Disks()
  def getFirmware = new Firmware()
  def getMemory = new Memory()
  def getNetwork = new Network()
  def getPowerSource = new PowerSource()
  def getSensors = new Sensors()
  def getSoundCards = new SoundCard()
  def getUsbDevices = new UsbDevice()
}
