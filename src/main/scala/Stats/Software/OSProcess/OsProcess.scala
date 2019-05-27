package Stats.Software.OSProcess

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

object OsProcessState extends Enumeration {
  type OsProcessState = Value
  val NEW, OTHER, RUNNING, SLEEPING, STOPPED, WAITING, ZOMBIE = Value
}

case class OsProcessStats(cpuPercent: Double,
                          bytesRead: Long,
                          bytesWritten: Long,
                          commandLine: String,
                          currentWorkingDirectory: String,
                          group: String,
                          groupId: String,
                          kernelTime: Long,
                          name: String,
                          openFiles: Long,
                          parentProcessId: Int,
                          path: String,
                          priority: Int,
                          processId: Int,
                          residentSetSize: Long,
                          startTime: Long,
                          threadCount: Int,
                          upTime: Long,
                          user: String,
                          userID: String,
                          userTime: Long,
                          virtualSize: Long,
                         )

class OsProcess()(implicit val system: System) {
  def getOsProcessStatsAsync: Future[Array[OsProcessStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getOsProcessStats
      }
    }
  }

  def getOsProcessStats: Array[OsProcessStats] = {
    val osProcesses = system.getSystemInfo.getOperatingSystem.getProcesses(0, oshi.software.os.OperatingSystem.ProcessSort.CPU, true)
    osProcesses.map { process =>
      OsProcessStats(
        cpuPercent = process.calculateCpuPercent(),
        bytesRead = process.getBytesRead,
        bytesWritten = process.getBytesWritten,
        commandLine = process.getCommandLine,
        currentWorkingDirectory = process.getCurrentWorkingDirectory,
        group = process.getGroup,
        groupId = process.getGroupID,
        kernelTime = process.getKernelTime,
        name = process.getName,
        openFiles = process.getOpenFiles,
        parentProcessId = process.getParentProcessID,
        path = process.getPath,
        priority = process.getPriority,
        processId = process.getProcessID,
        residentSetSize = process.getResidentSetSize,
        startTime = process.getStartTime,
        threadCount = process.getThreadCount,
        upTime = process.getUpTime,
        user = process.getUser,
        userID = process.getUserID,
        userTime = process.getUserTime,
        virtualSize = process.getVirtualSize
      )
    }
  }
}
