package Stats

import java.util.concurrent.Executors

import oshi.SystemInfo

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService}

class SystemMetrics(private var executionContext: ExecutionContextExecutorService) {

  private val systemInfo: oshi.SystemInfo = new SystemInfo()

  /** Create new System object with default executionContext
    */
  def apply() = new SystemMetrics()

  /** Create new System object with default executionContext
    */
  def this() = this(ExecutionContext.fromExecutorService {
    Executors.newFixedThreadPool(8)
  })

  /** Create new System object with specified executionContext
    */
  def apply(executionContext: ExecutionContextExecutorService) = new SystemMetrics(executionContext)

  /** Get executionContext
    */
  def getExecutionContext: ExecutionContextExecutorService = executionContext

  /** Get systemInfo
    */
  def getSystemInfo: SystemInfo = systemInfo
}