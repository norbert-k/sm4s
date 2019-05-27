package Stats.Hardware.SoundCard

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

case class SoundCardStats(codec: String, driverVersion: String, name: String)

class SoundCard()(implicit val system: System) {
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

  def getSoundCardStatsAsync: Future[Array[SoundCardStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getSoundCardStats
      }
    }
  }
}
