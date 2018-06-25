package com.century.cleaner.data.repository

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.IPackageStatsObserver
import android.content.pm.PackageManager
import android.content.pm.PackageStats
import android.os.Build
import android.support.annotation.RequiresApi
import com.century.cleaner.data.model.CacheInfo
import com.century.cleaner.data.State
import com.century.cleaner.util.extension.hasO
import com.century.cleaner.util.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.lang.reflect.Method
import javax.inject.Inject

class CleanerRepositoryImplement @Inject constructor(
    private val context: Context,
    private val schedules: SchedulerProvider) : CleanerRepository {



  data class Info(
      val count: Int,
      val cacheInfos: List<CacheInfo>,
      val totalBytes: Long
  )

  private lateinit var getPackageSizeInfoMethod: Method
  override fun loadCaches() :Flowable<Info>{
    val totalCache: Long = 0L
    val cacheInfos = mutableListOf<CacheInfo>()
    val list = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    return Flowable.fromIterable(list)
        .flatMap { appInfo ->
            if (hasO()) {
              Flowable.just(getInfoO(appInfo, cacheInfos, totalCache))
            } else {
              Flowable.fromPublisher<Info> {subscriber ->
                getPackageSizeInfoMethod.invoke(context.packageManager, appInfo.packageName,
                    object : IPackageStatsObserver.Stub() {

                      @Suppress("DEPRECATION")
                      override fun onGetStatsCompleted(pStats: PackageStats?, succeeded: Boolean) {
                        if (succeeded && (pStats?.cacheSize!! > 0L)) {
                          val packageManager = context.packageManager
                          val cacheInfo = CacheInfo(
                              packageManager.getApplicationLabel(appInfo).toString(),
                              pStats.packageName,
                              pStats.cacheSize)
                          cacheInfos.add(cacheInfo)
                          totalCache.plus(pStats.cacheSize)

                          subscriber.onNext(Info(1, listOf(cacheInfo), pStats.cacheSize))
                          subscriber.onComplete()
                        }
                      }
                    })
              }

            }
        }
        .scan { acc: Info, info: Info ->
          Info(
              acc.count + info.count,
              acc.cacheInfos + info.cacheInfos,
              acc.totalBytes + info.totalBytes
          )
        }
        .subscribeOn(schedules.io())


  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun getInfoO(appInfo: ApplicationInfo,
      cacheInfos: MutableList<CacheInfo>,
      totalCache: Long): Info {
    val storageStatsManager = context.getSystemService(
        Context.STORAGE_STATS_SERVICE) as StorageStatsManager
    //val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
    val storageStats = storageStatsManager.queryStatsForUid(appInfo.storageUuid, appInfo.uid)
    val packageManager = context.packageManager
    val cacheInfo = CacheInfo(
        packageManager.getApplicationLabel(appInfo).toString(),
        appInfo.packageName,
        storageStats.cacheBytes)
    cacheInfos.add(cacheInfo)
    totalCache.plus(storageStats.cacheBytes)

    return Info(1, listOf(cacheInfo), storageStats.cacheBytes)
  }

  override fun clearCaches() {

  }

}