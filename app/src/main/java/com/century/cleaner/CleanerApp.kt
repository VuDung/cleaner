package com.century.cleaner

import android.app.Application
import com.century.cleaner.di.AppModule
import com.century.cleaner.di.DaggerAppComponent
import com.century.incoming.daggers.Daggers
import timber.log.Timber
import timber.log.Timber.DebugTree
import android.util.Log


class CleanerApp : Application(){
  override fun onCreate() {
    super.onCreate()

    configureDagger()

    configureTimber()
  }

  private fun configureTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    } else {
      Timber.plant(CrashReportingTree())
    }
  }

  private fun configureDagger() {
    Daggers.configure(this)
    Daggers.openAppScope(DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build())
    Daggers.injectAppDependencies(this)
  }

  inner class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return
      }

      FakeCrashLibrary.log(priority, tag, message)

      if (t != null) {
        if (priority == Log.ERROR) {
          FakeCrashLibrary.logError(t)
        } else if (priority == Log.WARN) {
          FakeCrashLibrary.logWarning(t)
        }
      }
    }
  }

  object FakeCrashLibrary{

      fun log(priority: Int, tag: String?, message: String) {
      }

      fun logWarning(t: Throwable) {
      }

      fun logError(t: Throwable) {
      }

  }
}