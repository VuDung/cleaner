package com.century.cleaner.util

import android.app.ActivityManager
import android.content.Context
import java.io.BufferedReader
import java.io.FileReader

object MemoryUtils{

  fun getMemoryInfo(context: Context): ActivityManager.MemoryInfo{
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)
    return memoryInfo
  }

  fun getAvailMemory(context: Context): Long{
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)
    return memoryInfo.availMem
  }

  fun getTotalMemory(context: Context): Long {
    val file = "/proc/meminfo"
    val memInfo: String
    val strs: Array<String>
    var memory: Long = 0

    try {
      val fileReader = FileReader(file)
      val bufferedReader = BufferedReader(fileReader, 8192)
      memInfo = bufferedReader.readLine()
      strs = memInfo.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

      memory = Integer.valueOf(strs[1]).toInt().toLong()
      bufferedReader.close()
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return memory * 1024
  }
}