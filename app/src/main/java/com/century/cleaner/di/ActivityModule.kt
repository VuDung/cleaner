package com.century.cleaner.di

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.century.cleaner.data.repository.CleanerRepository
import com.century.cleaner.feature.cleaner.CleanerViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

  @Provides
  fun viewModelFactory(cleanerRepository: CleanerRepository) = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(CleanerViewModel::class.java)) {
        @Suppress("UNCHECKED_CAST")
        return CleanerViewModel(cleanerRepository) as T
      }
      throw IllegalAccessException("unknown viewmodel $modelClass")
    }
  }

}