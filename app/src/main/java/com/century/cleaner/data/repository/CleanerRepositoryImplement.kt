package com.century.cleaner.data.repository

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.pm.IPackageStatsObserver
import android.content.pm.PackageManager
import android.content.pm.PackageStats
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
    private val schedules: SchedulerProvider): CleanerRepository {

  override val mTotalCache: Subject<Long>
    get() = PublishSubject.create()
  override val mCount: Subject<IntArray>
    get() = PublishSubject.create()
  override val mCacheInfos: Subject<MutableList<CacheInfo>>
    get() = PublishSubject.create()
  override val mState: Subject<State>
    get() = PublishSubject.create()

  private lateinit var getPackageSizeInfoMethod: Method
  override fun loadCaches() {
    val counts: IntArray = intArrayOf(2)
    val totalCache: Long = 0L
    val cacheInfos = mutableListOf<CacheInfo>()
    mState.onNext(State.inProgress())
    Flowable.just(context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA))
        .flatMap { it ->
          counts[0] = 0
          counts[1] = it.size
          Flowable.fromIterable(it)
        }
        .map { appInfo -> {
          if(hasO()){
            val storageStatsManager = context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
            //val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val storageStats = storageStatsManager.queryStatsForUid(appInfo.storageUuid, appInfo.uid)
            val packageManager = context.packageManager
            val cacheInfo = CacheInfo(
                packageManager.getApplicationLabel(appInfo).toString(),
                appInfo.packageName,
                storageStats.cacheBytes)
            cacheInfos.add(cacheInfo)
            counts[0].inc()
            totalCache.plus(storageStats.cacheBytes)

            mCacheInfos.onNext(cacheInfos)
            mTotalCache.onNext(totalCache)
            mCount.onNext(counts)
          }else {
            getPackageSizeInfoMethod.invoke(context.packageManager, appInfo.packageName,
                object : IPackageStatsObserver.Stub() {
                  @Suppress("DEPRECATION")
                  override fun onGetStatsCompleted(pStats: PackageStats?, succeeded: Boolean) {
                    if(succeeded && (pStats?.cacheSize!! > 0L)){
                      val packageManager = context.packageManager
                      val cacheInfo = CacheInfo(
                          packageManager.getApplicationLabel(appInfo).toString(),
                          pStats.packageName,
                          pStats.cacheSize)
                      /*
                      cacheInfos.subscribe{ list -> list.add(cacheInfo)}
                      totalCache.subscribe { lastValue -> lastValue.plus(pStats.cacheSize) }
                      count.subscribe { c -> c.inc() }
                      */
                      cacheInfos.add(cacheInfo)
                      counts[0].inc()
                      totalCache.plus(pStats.cacheSize)

                      mCacheInfos.onNext(cacheInfos)
                      mTotalCache.onNext(totalCache)
                      mCount.onNext(counts)
                    }
                  }
                })
          }

        } }
        .subscribeOn(schedules.io())
        .doOnComplete { mState.onNext(State.success()) }
        .doOnError { t: Throwable? -> mState.onNext(
            State.failure(t?.message, t)) }



  }

  override fun clearCaches() {

  }

}