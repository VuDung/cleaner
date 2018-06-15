package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.century.cleaner.data.State
import com.century.incoming.daggers.Daggers
import timber.log.Timber
import javax.inject.Inject

class FragmentCleaner : Fragment(){
  @Inject lateinit var modelProvider: ViewModelProvider
  private val model: CleanerViewModel by lazy {
    modelProvider.get(CleanerViewModel::class.java)
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Daggers.inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    model.states.subscribe {
      state ->
      when(state){
        is State.InProgress -> {
          model.counts.subscribe { counts -> Timber.i("progress %d/%d", counts[0], counts[1]) }
          model.totalCache.subscribe { t: Long? -> Timber.i("Scan cache %d", t) }
        }
        is State.Success -> {
          model.cacheInfos.subscribe { it -> Timber.i("app cache size %d", it.size) }
        }
        is State.Failure -> {
          model.states.subscribe { failer -> Timber.i("Failed", failer) }
        }
      }

    }
  }
}