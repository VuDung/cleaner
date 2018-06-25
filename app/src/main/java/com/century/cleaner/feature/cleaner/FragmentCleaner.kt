package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
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
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: CleanerViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory)
        .get(CleanerViewModel::class.java)
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
//    viewModel.states.subscribe {
//      state ->
//      when(state){
//        is State.InProgress -> {
//          viewModel.counts.subscribe { counts -> Timber.i("progress %d/%d", counts[0], counts[1]) }
//          viewModel.totalCache.subscribe { t: Long? -> Timber.i("Scan cache %d", t) }
//        }
//        is State.Success -> {
//          viewModel.cacheInfos.subscribe { it -> Timber.i("app cache size %d", it.size) }
//        }
//        is State.Failure -> {
//          viewModel.states.subscribe { failer -> Timber.i("Failed", failer) }
//        }
//      }
//
//    }
    viewModel.infoLive.observe(this, Observer {
      Timber.i("count ${it?.count} size ${it?.totalBytes}")
    })
  }
}