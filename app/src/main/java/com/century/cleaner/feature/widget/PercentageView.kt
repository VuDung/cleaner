package com.century.cleaner.feature.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PercentageView : View{
  private var paint = Paint()
  private var mWidth = 0
  private var mHeight = 0
  private var progress = 20
  private var progressColor = Color.BLUE
  private val secondProgressColor = Color.parseColor("#80000000")//Color.rgb(72, 106, 176)

  constructor(context: Context): this(context, null)
  constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle)

  fun setProgress(progress: Int){
    this.progress = progress
    invalidate()
  }

  fun setProgress(progress: Int, progressColor: Int){
    this.progress = progress
    this.progressColor = progressColor
    invalidate()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    mWidth = MeasureSpec.getSize(widthMeasureSpec)
    mHeight = MeasureSpec.getSize(heightMeasureSpec)
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    val widthProgress = (progress.div(100f)) * mWidth.toFloat()
    paint.color = progressColor
    canvas?.drawRect(0f, 0f, widthProgress, mHeight.toFloat(), paint)

    paint.color = secondProgressColor
    canvas?.drawRect(widthProgress, 0f, mWidth.toFloat(), mHeight.toFloat(), paint)

  }
}