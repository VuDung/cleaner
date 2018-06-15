package com.century.cleaner.di

import com.century.cleaner.CleanerApp
import dagger.Component

@Component(modules = [(AppModule::class)])
interface AppComponent{
  fun inject(app: CleanerApp)
  fun plus(module: ActivityModule): ActivityComponent
}