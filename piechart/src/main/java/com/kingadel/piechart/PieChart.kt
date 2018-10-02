package com.kingadel.piechart

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class PieChart : View {

    private var pieAdapter: PieChartAdapter? = null

    fun setAdapter(adapter: PieChartAdapter) {
        pieAdapter = adapter
        invalidate()
    }

    private val rectF by lazy {
        RectF(pieAdapter!!.offset, pieAdapter!!.offset, width.toFloat() - pieAdapter!!.offset, height.toFloat() - pieAdapter!!.offset)
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth.toFloat()
        val height = measuredHeight.toFloat()


//        Timber.d("width: $width , height: $height")
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (pieAdapter != null) {
            pieAdapter!!.drawData.forEach {
                canvas.apply {
                    drawArc(rectF,
                            it.startingAngle,
                            it.drawingAngle,
                            false, it.paint)
                }
            }
        }
    }

}
