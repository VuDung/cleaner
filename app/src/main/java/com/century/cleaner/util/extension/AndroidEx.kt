package com.century.cleaner.util.extension

import android.os.Build

fun hasKitkat() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
fun hasL() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
fun hasO() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
fun hasJellyBeanMr2() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
fun hasJellyBeanMr1() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1