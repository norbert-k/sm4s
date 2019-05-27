package Stats.Hardware.SoundCard

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

/** Soundcard stats & info
  *
  * @constructor create new SoundCardStats
  * @param codec         Codec of the Sound card
  * @param driverVersion Current and complete name of the driver version
  * @param name          Name of the card.
  *
  */
case class SoundCardStats(codec: String, driverVersion: String, name: String)

class SoundCard()(implicit val system: System) {
  /** Get SoundCardStats asynchronously
    */
  def getSoundCardStatsAsync: Future[Array[SoundCardStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getSoundCardStats
      }
    }
  }

  /** Get SoundCardStats synchronously
    */
  def getSoundCardStats: Array[SoundCardStats] = {
    val soundCards = system.getSystemInfo.getHardware.getSoundCards
    soundCards.map { soundCard =>
      SoundCardStats(
        codec = soundCard.getCodec,
        driverVersion = soundCard.getDriverVersion,
        name = soundCard.getName
      )
    }
  }
}
