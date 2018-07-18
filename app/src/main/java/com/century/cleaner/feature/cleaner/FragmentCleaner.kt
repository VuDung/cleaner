package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.century.cleaner.R
import com.century.cleaner.feature.cleaner.CleanerViewModel.CleanerState.Create
import com.century.incoming.daggers.Daggers
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
    return inflater.inflate(R.layout.fragment_cleaner, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    lifecycle.addObserver(viewModel)

    viewModel.state.observe(this, Observer {

    })
  }
}