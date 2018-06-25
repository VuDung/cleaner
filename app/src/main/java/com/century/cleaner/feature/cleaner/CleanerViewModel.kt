package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import androidx.lifecycle.mutableLiveData
import com.century.cleaner.data.repository.CleanerRepository
import com.century.cleaner.data.repository.CleanerRepositoryImplement.Info
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CleanerViewModel @Inject constructor(
    private val cleanerRespository: CleanerRepository) : ViewModel(), LifecycleObserver {
  internal var infoLive: LiveData<Info> = mutableLiveData()
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate() {
    infoLive = LiveDataReactiveStreams.fromPublisher(cleanerRespository.loadCaches())

  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}