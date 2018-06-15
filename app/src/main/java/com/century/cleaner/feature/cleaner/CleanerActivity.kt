package com.century.cleaner.feature.cleaner

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.century.cleaner.R
import com.century.cleaner.base.DaggerActivity
import com.century.incoming.daggers.Daggers
import javax.inject.Inject

class CleanerActivity : DaggerActivity(){
  //@Inject lateinit var model: CleanerViewModel
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cleaner)
    Daggers.inject(this)

    supportFragmentManager.beginTransaction()
        .replace(R.id.content, FragmentCleaner())
        .commit()
  }


}