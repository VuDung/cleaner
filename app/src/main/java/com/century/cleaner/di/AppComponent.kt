package com.century.cleaner.di

import com.century.cleaner.CleanerApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent{
  fun inject(app: CleanerApp)
  fun plus(module: ActivityModule): ActivityComponent
}