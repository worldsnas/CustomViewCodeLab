package mosleh.saeed.widget.circleeffect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CircleEffect : View {

    private val drawCircle = false // false draw spinning rect.
    private val duration = 1500 // ms
    private val alphaHelper by lazy {
        (duration / 230)
    }
    val circles = ArrayList<Circle>()
    private val paint = Paint().apply {
        //        color = Color.BLACK
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        Observable.interval(0, 16, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    invalidate()
                }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!drawCircle)
            canvas.save()

        circles.forEach {
            val showTime = (System.currentTimeMillis() - it.creationTime).toFloat()
            if (showTime < duration) {
                val radius = (showTime * 3 / 20f) + 50
                paint.color = it.color
//                val bb = (showTime / 4.8).toInt()
//                if (bb < 255)
                paint.alpha = 255 - (showTime / alphaHelper).toInt()
//                else
//                    return@forEach
                if (drawCircle)
                    canvas.drawCircle(it.x, it.y, radius, paint)
                else {
                    canvas.restore()
                    canvas.save()

                    canvas.rotate(showTime / duration * 360f, it.x, it.y)
                    canvas.drawRect(it.x - radius, it.y - radius, it.x + radius, it.y + radius, paint)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                circles.add(Circle(event.x, event.y, System.currentTimeMillis()))
                invalidate()
            }
            MotionEvent.ACTION_UP -> {

            }
            MotionEvent.ACTION_MOVE -> {
                circles.add(Circle(event.x, event.y, System.currentTimeMillis()))
                invalidate()
            }
        }
        return true
    }
}
