package com.century.cleaner.util.extension

import android.view.View

fun View.show() {
  visibility = View.VISIBLE
}

fun View.hide() {
  visibility = View.GONE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun View.onClick(funtion: () -> Unit){
  setOnClickListener { funtion() }
}