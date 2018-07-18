package com.century.cleaner.feature

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import android.widget.TextView
import com.century.cleaner.R
import com.century.cleaner.base.DaggerActivity
import com.century.cleaner.feature.apps.AppManagerActivity
import com.century.cleaner.feature.battery.BatteryActivity
import com.century.cleaner.feature.booster.BoosterActivity
import com.century.cleaner.feature.cleaner.CleanerActivity
import com.century.cleaner.feature.widget.PercentageView
import com.century.cleaner.util.MemoryUtils
import com.century.cleaner.util.StorageUtils
import com.century.cleaner.util.extension.hasStatAccessOption
import com.century.cleaner.util.extension.isStatAccessPermissionGranted
import com.century.cleaner.util.extension.onClick
import com.century.cleaner.util.extension.setTextColorSpan
import com.century.cleaner.util.extension.start
import com.century.cleaner.util.extension.takeColor
import kotterknife.bindView
import timber.log.Timber

class MainActivity : DaggerActivity() {
  private val cleaner: LinearLayout by bindView(R.id.cleaner)
  private val booster: LinearLayout by bindView(R.id.booster)
  private val apps: LinearLayout by bindView(R.id.apps)
  private val battery: LinearLayout by bindView(R.id.battery)
  private val toolbar: Toolbar by bindView(R.id.toolbar)
  private val storage: PercentageView by bindView(R.id.storage)
  private val ram: PercentageView by bindView(R.id.ram)
  private val tvStorageInfo: TextView by bindView(R.id.tvStorageInfo)
  private val tvRamInfo: TextView by bindView(R.id.tvRamInfo)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val green = takeColor(R.color.green_500)
    val blue = takeColor(R.color.blue_500)
    val red = takeColor(R.color.red_500)

    val sdCard = StorageUtils.getSDCardStorage()
    val system = StorageUtils.getSystemStorage()

    val total = sdCard[1] + system[1]
    val free = sdCard[0] + system[0]

    val storagePercent = (total - free) / total.toDouble() * 100
    val storageColor = if (storagePercent <= 50) green else if (storagePercent <= 85) blue else red
    Timber.i("Storage used %s total %s percentage %d", StorageUtils.convertStorage(total - free),
        StorageUtils.convertStorage(total), storagePercent.toInt())
    storage.setProgress(storagePercent.toInt(), storageColor)
    val usedStorage = storagePercent.toInt().toString().plus("% used")
    val infoStorage = usedStorage.plus(" | ").plus(StorageUtils.convertStorage(free)).plus(" free")
    tvStorageInfo.setTextColorSpan(infoStorage, usedStorage, storageColor)

    val memoryInfo = MemoryUtils.getMemoryInfo(this)
    val usedMem = memoryInfo.totalMem - memoryInfo.availMem
    val totalMem = memoryInfo.totalMem
    val ramPercent = usedMem / totalMem.toDouble() * 100
    val ramColor = if (ramPercent <= 85) green else if (ramPercent <= 93) blue else red
    Timber.i("RAM used %s total %s percentage %d", StorageUtils.convertStorage(usedMem),
        StorageUtils.convertStorage(totalMem), ramPercent.toInt())
    ram.setProgress(ramPercent.toInt(), ramColor)
    val usedRam = ramPercent.toInt().toString().plus("% used")
    val infoRam = usedRam.plus(" | ").plus(StorageUtils.convertStorage(memoryInfo.availMem)).plus(
        " free")
    tvRamInfo.setTextColorSpan(infoRam, usedRam, ramColor)


    cleaner.onClick { start<CleanerActivity>() }
    booster.onClick { start<BoosterActivity>() }
    apps.onClick { start<AppManagerActivity>() }
    battery.onClick { start<BatteryActivity>() }

    if(!isStatAccessPermissionGranted() && hasStatAccessOption()){
      val builder = AlertDialog.Builder(this)
      builder.apply {
        setTitle(getString(R.string.dialog_android_8_detected))
        setMessage(getString(R.string.dialog_android_8_detected_msg))
        setPositiveButton(getString(R.string.dialog_grant_permission)) { _, _ ->
          startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        setNegativeButton(getString(R.string.dialog_not_now), null)
      }
      builder.create().show()

    }
  }
}
