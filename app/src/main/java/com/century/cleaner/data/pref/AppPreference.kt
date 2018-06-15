package com.century.cleaner.data.pref

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by CUSDungVT on 3/7/2017.
 */

class AppPreference(context: Context) {
  private val preferenceUtils: PreferenceUtils = PreferenceUtils(context)

  fun registerPreferenceChangeListener(
      listener: SharedPreferences.OnSharedPreferenceChangeListener) {
    preferenceUtils.registerPreferenceChangeListener(listener)
  }

  fun unregisterPreferenceChangeListener(
      listener: SharedPreferences.OnSharedPreferenceChangeListener) {
    preferenceUtils.registerPreferenceChangeListener(listener)
  }


}
