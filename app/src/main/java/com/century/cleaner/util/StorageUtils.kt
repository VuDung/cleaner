package com.century.cleaner.util

import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.century.cleaner.util.extension.hasJellyBeanMr2

object StorageUtils{
  fun convertStorage(size: Long): String {
    val kb: Long = 1024
    val mb = kb * 1024
    val gb = mb * 1024

    when {
      size >= gb -> return String.format("%.1f GB", size.toFloat() / gb)
      size >= mb -> {
        val f = size.toFloat() / mb
        return String.format(if (f > 100) "%.0f MB" else "%.1f MB", f)
      }
      size >= kb -> {
        val f = size.toFloat() / kb
        return String.format(if (f > 100) "%.0f KB" else "%.1f KB", f)
      }
      else -> return String.format("%d B", size)
    }
  }


  fun getSDCardStorage(): LongArray {
    if (Environment.isExternalStorageRemovable()) {
      val sDcString = Environment.getExternalStorageState()
      if (sDcString == Environment.MEDIA_MOUNTED) {
        val pathFile = Environment
            .getExternalStorageDirectory()

        try {
          val statfs = StatFs(pathFile.path)
          val nTotalBlocks = if(hasJellyBeanMr2()) statfs.blockCountLong else statfs.blockCount.toLong()
          val nBlocSize = if(hasJellyBeanMr2()) statfs.blockSizeLong else statfs.blockSize.toLong()
          val nAvailaBlock = if(hasJellyBeanMr2()) statfs.availableBlocksLong else statfs.availableBlocks.toLong()
          val nFreeBlock = if(hasJellyBeanMr2()) statfs.freeBlocksLong else statfs.freeBlocks.toLong()

          val total = nTotalBlocks * nBlocSize
          val free = nAvailaBlock * nBlocSize

          return longArrayOf(free, total)
        } catch (e: IllegalArgumentException) {
          e.printStackTrace()
        }

      }
    }
    return longArrayOf(0L, 0L)
  }

  fun getSystemStorage(): LongArray {
    val path = Environment.getDataDirectory()
    val stat = StatFs(path.path)
    val blockSize = if(hasJellyBeanMr2()) stat.blockSizeLong else stat.blockSize.toLong()
    val totalBlocks = if(hasJellyBeanMr2()) stat.blockCountLong else stat.blockCount.toLong()
    val availableBlocks = if(hasJellyBeanMr2()) stat.availableBlocksLong else stat.availableBlocks.toLong()

    val totalSize = blockSize * totalBlocks
    val availSize = availableBlocks * blockSize

    return longArrayOf(availSize, totalSize)


  }
}