package com.century.cleaner.feature.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class SquareFrameLayout : FrameLayout{
  constructor(context: Context): this(context, null)
  constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle)

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val width = MeasureSpec.getSize(widthMeasureSpec)
    val height = MeasureSpec.getSize(heightMeasureSpec)
    val size = Math.min(width, height)
    super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY))
  }
}