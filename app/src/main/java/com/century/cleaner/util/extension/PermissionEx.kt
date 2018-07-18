package com.century.cleaner.util.extension

import android.annotation.TargetApi
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings

@TargetApi(Build.VERSION_CODES.O)
fun Context.isStatAccessPermissionGranted(): Boolean{
  val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
  val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
  return if(mode == AppOpsManager.MODE_DEFAULT){
    checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED
  }else{
    mode == AppOpsManager.MODE_ALLOWED
  }
}

fun Context.hasStatAccessOption(): Boolean{
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return list.size > 0
  }
  return false
}