package com.century.cleaner.feature.cleaner

import android.os.Bundle
import com.century.cleaner.R
import com.century.cleaner.base.DaggerActivity
import com.century.incoming.daggers.Daggers
import kotlinx.android.synthetic.main.activity_main.*

class CleanerActivity : DaggerActivity(){
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cleaner)
    Daggers.inject(this)

    toolbar.apply {
      setNavigationOnClickListener { onBackPressed() }
    }
    setSupportActionBar(toolbar)

    supportFragmentManager.beginTransaction()
        .replace(R.id.content, FragmentCleaner())
        .commit()
  }


}