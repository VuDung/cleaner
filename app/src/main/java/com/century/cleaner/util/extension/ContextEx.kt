package com.century.cleaner.util.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.BoolRes
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.support.v4.content.ContextCompat

fun Context.takeColor(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.takeBool(@BoolRes boolRes: Int): Boolean = resources.getBoolean(boolRes)

fun Context.takeInteger(@IntegerRes integerRes: Int): Int = resources.getInteger(integerRes)

fun Context.takeDrawable(@DrawableRes drawableRes: Int): Drawable {
  return ContextCompat.getDrawable(this, drawableRes)!!
}

inline fun <reified T> Context.start() = this.startActivity(android.content.Intent(this, T::class.java))