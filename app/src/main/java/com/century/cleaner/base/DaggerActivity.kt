package com.century.cleaner.base

import android.support.v7.app.AppCompatActivity
import com.century.cleaner.di.ActivityModule
import com.century.incoming.daggers.HasScope

abstract class DaggerActivity : AppCompatActivity(), HasScope{

  override fun module() = ActivityModule(this)
}