package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.century.cleaner.data.State
import com.century.cleaner.util.extension.hasStatAccessOption
import com.century.cleaner.util.extension.isStatAccessPermissionGranted
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

    if(!activity!!.isStatAccessPermissionGranted() && activity!!.hasStatAccessOption()){
      startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }
    lifecycle.addObserver(viewModel)
  }
}