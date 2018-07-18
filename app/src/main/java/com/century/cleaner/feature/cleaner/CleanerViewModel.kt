package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import androidx.lifecycle.mutableLiveData
import com.century.cleaner.data.model.TotalCache
import com.century.cleaner.data.repository.CleanerRepository
import com.century.cleaner.feature.cleaner.CleanerViewModel.CleanerState.Complete
import com.century.cleaner.feature.cleaner.CleanerViewModel.CleanerState.Error
import com.century.cleaner.feature.cleaner.CleanerViewModel.CleanerState.Scanning
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class CleanerViewModel @Inject constructor(
  private val cleanerRespository: CleanerRepository) : ViewModel(), LifecycleObserver {
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  val state = mutableLiveData<CleanerState>(CleanerState.Create())

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate() {
    cleanerRespository.loadCaches().subscribe({ info ->
      if (info.currentCount == info.totalCount) {
        state.value = Complete(info)
      } else {
        state.value = Scanning(info.currentCount, info.currentPackage, info.totalBytes)
      }
      Timber.i("package: ${info.currentPackage}")
      Timber.i("count ${info.currentCount}/${info.totalCount} size ${info.totalBytes}")
    },
      {
        it.printStackTrace()
        state.value = Error(it.message)
      }).addTo(compositeDisposable)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  sealed class CleanerState {
    class Create : CleanerState()
    class Scanning(
      val currentCount: Int,
      val currentPackage: String,
      val totalCacheSize: Long
    ) : CleanerState()

    class Error(reason: String?) : CleanerState()
    class Complete(
      val totalCache: TotalCache
    ) : CleanerState()
  }
}