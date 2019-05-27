package Stats.Hardware.SoundCard

import Stats.{DefaultSource, SystemMetrics}

import scala.concurrent.Future

/** SoundCardStats source for AKKA Streams
  */
class SoundCardSource()(implicit val system: SystemMetrics) extends DefaultSource[Array[SoundCardStats]] {
  override val outletName: String = "SoundCardSource"

  val soundCards = new SoundCard()

  override def getStatsAsync: Future[Array[SoundCardStats]] = {
    soundCards.getSoundCardStatsAsync
  }
}

