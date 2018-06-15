package com.century.cleaner.di

import com.century.cleaner.feature.cleaner.CleanerActivity
import com.century.cleaner.feature.cleaner.FragmentCleaner
import dagger.Subcomponent

@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent{
  fun inject(cleanerActivity: CleanerActivity)
  fun inject(fragmentCleaner: FragmentCleaner)
}