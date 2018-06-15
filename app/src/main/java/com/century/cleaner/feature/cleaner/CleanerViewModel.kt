package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import com.century.cleaner.data.repository.CleanerRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CleanerViewModel @Inject constructor(
    private val cleanerRespository: CleanerRepository) : ViewModel(), LifecycleObserver {

  val states = cleanerRespository.mState
  val counts = cleanerRespository.mCount
  val totalCache = cleanerRespository.mTotalCache
  val cacheInfos = cleanerRespository.mCacheInfos

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate() {
    cleanerRespository.loadCaches()

  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}