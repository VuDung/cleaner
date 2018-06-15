package com.century.incoming.daggers

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

internal class DaggerActivityLifecycleCallbacks : ActivityLifecycleCallbacks {
  private var scopeOpened: Boolean = false


  override fun onActivityPaused(activity: Activity?) {}

  override fun onActivityResumed(activity: Activity?) {}

  override fun onActivityStarted(activity: Activity?) {}

  override fun onActivityDestroyed(activity: Activity?) {
    if (activity is HasScope && activity.isFinishing) {
      Daggers.closeActivityScope()
      scopeOpened = false
    }
  }

  override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    if (activity is HasScope) {
      outState?.putBoolean(DAGGERS_KEY_SCOPE_OPENED, scopeOpened)
    }
  }

  override fun onActivityStopped(activity: Activity?) {}

  override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    if (activity is HasScope && scopeIsNotOpened(savedInstanceState)) {
      Daggers.openActivityScope((activity as HasScope).module())
      scopeOpened = true
    }
  }

  private fun scopeIsNotOpened(savedInstanceState: Bundle?): Boolean {
    return savedInstanceState == null || !savedInstanceState.getBoolean(DAGGERS_KEY_SCOPE_OPENED)
  }

  companion object {

    private val DAGGERS_KEY_SCOPE_OPENED = "daggers:hasScope"
  }
}
