package Stats

import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}

import scala.concurrent.Future

/** Abstract class to implement SourceShape[T]
  */
abstract class DefaultSource[T] extends GraphStage[SourceShape[Future[T]]] {
  override val shape: SourceShape[Future[T]] = SourceShape.of(out)
  /** Outlet name
    */
  val outletName: String
  val out: Outlet[Future[T]] = Outlet.create(outletName)

  /** Function to get stats asynchronously
    */
  def getStatsAsync: Future[T]

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = {
    new GraphStageLogic(shape) {

      setHandler(out, new OutHandler {
        override def onPull(): Unit = {
          push(out, getStatsAsync)
        }
      })
    }
  }
}
