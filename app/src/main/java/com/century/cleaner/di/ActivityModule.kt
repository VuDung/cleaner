package com.century.cleaner.di

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.century.cleaner.feature.cleaner.CleanerViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: FragmentActivity){

  @Provides fun viewModelFactory() = ViewModelProviders.of(activity)
  @Provides fun cleanerViewModel() = ViewModelProviders.of(activity).get(CleanerViewModel::class.java)
}