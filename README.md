# sm4s

#### Stream CPU Usage
```scala
object Main extends App {
  // Print out CPU Usage every second
  val cpuDynamicSource = Source.fromGraph(new CPUDynamicSource)
  Source.tick(1.second, 1.second, 0).zip(cpuDynamicSource).map(_._2).map(x => {
    x.onComplete{
      case Success(value) => println(value)
      case Failure(exception) => println(exception)
    }
  }).runWith(Sink.ignore)
}
```