package com.century.cleaner.data.repository

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.IPackageStatsObserver
import android.content.pm.PackageManager
import android.content.pm.PackageStats
import android.os.Build
import android.support.annotation.RequiresApi
import com.century.cleaner.data.model.SingleCache
import com.century.cleaner.data.model.TotalCache
import com.century.cleaner.util.extension.hasO
import com.century.cleaner.util.extension.isStatAccessPermissionGranted
import com.century.cleaner.util.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.lang.reflect.Method
import javax.inject.Inject

class CleanerRepositoryImplement @Inject constructor(
    private val context: Context,
    private val schedules: SchedulerProvider) : CleanerRepository {

  private var getPackageSizeInfoMethod: Method? = null
  override fun loadCaches() :Flowable<TotalCache>{
    val totalCache: Long = 0L
    val cacheInfos = mutableListOf<SingleCache>()
    val list = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    val totalCount = list.size
    return Flowable.fromIterable(list)
        .flatMap { appInfo ->
            if (hasO() && context.isStatAccessPermissionGranted()) {
              Flowable.just(getInfoO(appInfo, cacheInfos, totalCache, totalCount))
            } else {
              Flowable.fromPublisher<TotalCache> {subscriber ->
                getPackageSizeInfoMethod?.invoke(context.packageManager, appInfo.packageName,
                    object : IPackageStatsObserver.Stub() {

                      @Suppress("DEPRECATION")
                      override fun onGetStatsCompleted(pStats: PackageStats?, succeeded: Boolean) {
                        if (succeeded && (pStats?.cacheSize!! > 0L)) {
                          val packageManager = context.packageManager
                          val cacheInfo = SingleCache(
                              packageManager.getApplicationLabel(appInfo).toString(),
                              pStats.packageName,
                              pStats.cacheSize)
                          cacheInfos.add(cacheInfo)
                          totalCache.plus(pStats.cacheSize)

                          subscriber.onNext(TotalCache(1, listOf(cacheInfo), pStats.cacheSize, totalCount))
                          subscriber.onComplete()
                        }
                      }
                    })
              }

            }
        }
        .scan { acc: TotalCache, info: TotalCache ->
          TotalCache(
              acc.count + info.count,
              acc.singleCaches + info.singleCaches,
              acc.totalBytes + info.totalBytes,
            acc.totalCount
          )
        }
        .subscribeOn(schedules.io())
        .observeOn(AndroidSchedulers.mainThread())


  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun getInfoO(appInfo: ApplicationInfo,
                       singleCaches: MutableList<SingleCache>,
                       totalCache: Long,
                       totalCount: Int): TotalCache {
    val storageStatsManager = context.getSystemService(
        Context.STORAGE_STATS_SERVICE) as StorageStatsManager
    //val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
    val storageStats = storageStatsManager.queryStatsForUid(appInfo.storageUuid, appInfo.uid)
    val packageManager = context.packageManager
    val cacheInfo = SingleCache(
        packageManager.getApplicationLabel(appInfo).toString(),
        appInfo.packageName,
        storageStats.cacheBytes)
    singleCaches.add(cacheInfo)
    totalCache.plus(storageStats.cacheBytes)

    return TotalCache(1, listOf(cacheInfo), storageStats.cacheBytes, totalCount)
  }

  override fun clearCaches() {

  }

}