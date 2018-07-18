package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import com.century.cleaner.data.repository.CleanerRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class CleanerViewModel @Inject constructor(
  private val cleanerRespository: CleanerRepository) : ViewModel(), LifecycleObserver {
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate() {
    cleanerRespository.loadCaches().subscribe({ info ->
      Timber.i("count ${info.count}/${info.totalCount} size ${info.totalBytes}")
    },
      Throwable::printStackTrace,
      { Timber.i("complete") })
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}