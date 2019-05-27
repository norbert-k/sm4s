package Stats.Hardware.SoundCard

import Stats.{DefaultSource, System}

import scala.concurrent.Future

class SoundCardSource()(implicit val system: System) extends DefaultSource[Array[SoundCardStats]] {
  override val outletName: String = "SoundCardSource"

  val soundCards = new SoundCard()

  override def getStatsAsync: Future[Array[SoundCardStats]] = {
    soundCards.getSoundCardStatsAsync
  }
}

