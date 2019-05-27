# sm4s
###### System metrics for Scala
sms4s is (*JNA-based*, native) Operating System and hardware information library for Scala thats based on [OSHI](https://github.com/oshi/oshi)
. It provides non-blocking (async) wrappers around blocking OSHI calls, while also providing **Akka** stream sources for interop with **Akka** based systems.
#### Non-blocking, asynchronous examples

```scala
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  import Stats.SystemMetrics
  import Stats.Stats
  
  implicit val systemMetrics: SystemMetrics = new SystemMetrics()
  implicit val executionContextExecutor: ExecutionContextExecutor = systemMetrics.getExecutionContext
  
  val stats: Stats = new Stats()
  
  // Get Memory Stats (async)
  stats.getHardware.getMemory.getMemoryStatsAsync.onComplete {
    case Success(value) => println(value)
    case Failure(exception) => System.err.println(exception)
  }
  
  // Get OS Stats (async)
  stats.getSoftware.getOperatingSystem.getOperatingSystemStatsAsync.onComplete {
    case Success(value) => println(value)
    case Failure(exception) => System.err.println(exception)
  }
}
```

#### Blocking, synchronous examples

```scala
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  import Stats.SystemMetrics
  import Stats.Stats
  
  implicit val systemMetrics: SystemMetrics = new SystemMetrics()
  
  val stats: Stats = new Stats()
  
  // Get Memory Stats (sync)
  println(stats.getHardware.getMemory.getMemoryStats)
  
  // Get OS Stats (sync)
  println(stats.getSoftware.getOperatingSystem.getOperatingSystemStats)
}
```

#### Stream example
```scala
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContextExecutor
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent.duration._
object Main extends App {
  import Stats.SystemMetrics
  import Stats.Stats
  import Stats.Hardware.CPU.CPUDynamicSource
  
  implicit val actorSystem: ActorSystem = ActorSystem("sm4s-example-actor-system")
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  
  implicit val systemMetrics: SystemMetrics = new SystemMetrics()
  implicit val executionContextExecutor: ExecutionContextExecutor = systemMetrics.getExecutionContext
  
  // New CPU Metrics Source
  val cpuMetricsSource = Source.fromGraph(new CPUDynamicSource)
  // Get and print out CPU Metrics every 5 seconds
  Source.tick(5.second, 5.second, 0).zip(cpuMetricsSource).map(_._2).map(cpuMetricFuture => {
    cpuMetricFuture.onComplete {
      case Success(value) => println(value)
      case Failure(exception) => System.err.println(exception)
    }
  }).runWith(Sink.ignore)
}
```