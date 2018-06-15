package com.century.cleaner.util.extension

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView

fun TextView.setTextColorSpan(fullText: String, subText: String, subTextColor: Int){
  val ss = SpannableString(fullText)
  val start = fullText.indexOf(subText)
  val end = start + subText.length
  ss.setSpan(ForegroundColorSpan(subTextColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
  text = ss
}

fun Drawable.tint(color: Int, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) {
  mutate().setColorFilter(color, mode)
}

var TextView.drawableStart: Drawable?
  get() = drawables[0]
  set(value) = setDrawables(value, drawableTop, drawableEnd, drawableBottom)

var TextView.drawableTop: Drawable?
  get() = drawables[1]
  set(value) = setDrawables(drawableStart, value, drawableEnd, drawableBottom)

var TextView.drawableEnd: Drawable?
  get() = drawables[2]
  set(value) = setDrawables(drawableStart, drawableTop, value, drawableBottom)

var TextView.drawableBottom: Drawable?
  get() = drawables[3]
  set(value) = setDrawables(drawableStart, drawableTop, drawableEnd, value)

private val TextView.drawables: Array<Drawable?>
  get() = if (hasJellyBeanMr1()) compoundDrawablesRelative else compoundDrawables

private fun TextView.setDrawables(start: Drawable?, top: Drawable?, end: Drawable?, buttom: Drawable?) {
  if (hasJellyBeanMr1())
    setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, buttom)
  else
    setCompoundDrawablesWithIntrinsicBounds(start, top, end, buttom)
}