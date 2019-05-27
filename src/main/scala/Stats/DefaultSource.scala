package Stats

import Stats.Hardware.Baseboard.Baseboard
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}

import scala.concurrent.Future

abstract class DefaultSource[T] extends GraphStage[SourceShape[Future[T]]] {
  val outletName: String
  val out: Outlet[Future[T]] = Outlet.create(outletName)
  override val shape: SourceShape[Future[T]] = SourceShape.of(out)

  def getStatsAsync: Future[T]

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = {
    new GraphStageLogic(shape) {

      setHandler(out, new OutHandler {
        override def onPull(): Unit = {
          push(out,getStatsAsync)
        }
      })
    }
  }
}
