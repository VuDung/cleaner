package com.century.cleaner.di

import android.app.Application
import com.century.cleaner.data.pref.AppPreference
import com.century.cleaner.data.repository.CleanerRepositoryImplement
import com.century.cleaner.util.rx.AppSchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application){
  @Provides @Singleton fun context() = app
  @Provides @Singleton fun appPrefs() = AppPreference(app)
  @Provides @Singleton fun appSchedules() = AppSchedulerProvider()
  @Provides @Singleton fun cleanerRepository(appScheduler: AppSchedulerProvider) = CleanerRepositoryImplement(app, appScheduler)
}