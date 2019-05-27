package Stats

import java.util.concurrent.Executors

import oshi.SystemInfo

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService}

class System {
  private val systemInfo: oshi.SystemInfo = new SystemInfo()

  private val executionContext: ExecutionContextExecutorService = ExecutionContext.fromExecutorService {
    Executors.newFixedThreadPool(8)
  }

  def getExecutionContext: ExecutionContextExecutorService = executionContext
  def getSystemInfo: SystemInfo = systemInfo
}
