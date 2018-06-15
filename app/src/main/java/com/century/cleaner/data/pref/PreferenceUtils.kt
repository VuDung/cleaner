package com.century.cleaner.data.pref

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by CUSDungVT on 12/26/2016.
 */

class PreferenceUtils internal constructor(private val context: Context) {

  fun registerPreferenceChangeListener(
      listener: SharedPreferences.OnSharedPreferenceChangeListener) {
    PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(
        listener)
  }

  fun unregisterPreferenceChangeListener(
      listener: SharedPreferences.OnSharedPreferenceChangeListener) {
    PreferenceManager.getDefaultSharedPreferences(
        context).unregisterOnSharedPreferenceChangeListener(listener)
  }

  /********* BOOLEAN  */
  fun getBooleanPreference(key: String, defaultValue: Boolean): Boolean {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue)
  }

  fun setBooleanPrefenence(key: String, value: Boolean) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply()
  }

  /********* INTEGER  */
  fun getIntPreference(key: String, defaultValue: Int): Int {
    return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue)
  }

  fun setIntPrefenence(key: String, value: Int) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply()
  }

  /********* LONG  */
  fun getLongPreference(key: String, defaultValue: Long): Long {
    return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, defaultValue)
  }

  fun setLongPrefenence(key: String, value: Long) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).apply()
  }

  /********* STRING  */
  fun getStringPreference(key: String, defaultValue: String): String? {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue)
  }

  fun setStringPrefenence(key: String, value: String) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply()
  }

  /********* FLOAT  */
  fun getFloatPreference(key: String, defaultValue: Float): Float {
    return PreferenceManager.getDefaultSharedPreferences(context).getFloat(key, defaultValue)
  }

  fun setFloatPrefenence(key: String, value: Float) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().putFloat(key, value).apply()
  }

  /********* CLEAR  */
  fun clearPreference(key: String) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).apply()
  }

  /********* CLEAR ALL  */
  fun getClearAllPreference() {
    PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply()
  }
}
