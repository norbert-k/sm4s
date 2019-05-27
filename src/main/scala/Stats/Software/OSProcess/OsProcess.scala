package Stats.Software.OSProcess

import Stats.System

import scala.concurrent.{ExecutionContextExecutorService, Future, blocking}

object OsProcessState extends Enumeration {
  type OsProcessState = Value
  val NEW, OTHER, RUNNING, SLEEPING, STOPPED, WAITING, ZOMBIE = Value
}

/** Network interface stats & info
  *
  * @constructor create new NetworkStats
  * @param cpuPercent              The proportion of up time that the process was executing in kernel or user mode.
  * @param bytesRead               Number of bytes the process has read from disk.
  * @param bytesWritten            Number of bytes the process has written to disk.
  * @param commandLine             Process command line
  * @param currentWorkingDirectory Current working directory. On Windows, this value is only populated for the current process.
  * @param group                   The group. On Windows systems, populating this value for processes other than the current user requires administrative privileges (and still may fail for some system processes)
  * @param groupId                 The group ID. On Windows systems, populating this value for processes other than the current user requires administrative privileges (and still may fail for some system processes)
  * @param kernelTime              Number of milliseconds the process has executed in kernel/system mode.
  * @param name                    Name of the process.
  * @param openFiles               Number of open file handles (or network connections) that belongs to the process
  * @param parentProcessId         ParentProcessID, if any; 0 otherwise.
  * @param path                    Full path of the executing process..
  * @param priority                Priority of this process. For Linux and Unix, priority is a value in the range -20 to 19 (20 on some systems). The default priority is 0; lower priorities cause more favorable scheduling. For Windows, priority values can range from 0 (lowest priority) to 31 (highest priority). Mac OS X has 128 priority levels, ranging from 0 (lowest priority) to 127 (highest priority). They are divided into several major bands: 0 through 51 are the normal levels; the default priority is 31. 52 through 79 are the highest priority regular threads; 80 through 95 are for kernel mode threads; and 96 through 127 correspond to real-time threads, which are treated differently than other threads by the scheduler.
  * @param processId               ProcessID.
  * @param residentSetSize         Resident Set Size (RSS). On Windows, returns the Private Working Set size. It is used to show how much memory is allocated to that process and is in RAM. It does not include memory that is swapped out. It does include memory from shared libraries as long as the pages from those libraries are actually in memory. It does include all stack and heap memory.
  * @param startTime               Start time of the process in number of milliseconds since January 1, 1970.
  * @param threadCount             Number of threads in this process.
  * @param upTime                  Number of milliseconds since the process started.
  * @param user                    User name. On Windows systems, also returns the domain prepended to the username.
  * @param userID                  UserID. On Windows systems, returns the Security ID (SID)
  * @param userTime                Number of milliseconds the process has executed in user mode.
  * @param virtualSize             Virtual Memory Size (VSZ). It includes all memory that the process can access, including memory that is swapped out and memory that is from shared libraries.
  *
  */
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
  /** Get ProcessStats asynchronously
    */
  def getOsProcessStatsAsync: Future[Array[OsProcessStats]] = {
    implicit val ec: ExecutionContextExecutorService = system.getExecutionContext
    Future {
      blocking {
        getOsProcessStats
      }
    }
  }

  /** Get ProcessStats synchronously
    */
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
