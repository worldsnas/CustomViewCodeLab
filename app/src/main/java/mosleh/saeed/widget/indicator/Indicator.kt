package mosleh.saeed.widget.indicator

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

class Indicator(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    init {

    }

    private val duration = 1500 // ms
    private var selectedPosition = 0
    private var totalCount = 5

    private var verticalPadding = 20
    private var horizontalPadding = 20

    private var horizontalDrawArea: Int = 0
    private var verticalDrawArea: Int = 0

    private var circleDiameter: Int = 0
    private var circleDefaultRadius: Int = 0
    private var circleAnimatingRadius: Int = 0

    private var lastSelectedPage: Int = 0

    private var selectedPage: Int = 0

    private val RADIUS_VALUE_ANIMATOR = "radius_value_animator"
    private val ALPHA_VALUE_ANIMATOR = "alpha_value_animator"


    //    var viewPager: ViewPager? =null
//    set(value) {
//        value!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(p0: Int) {
//
//            }
//
//            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
//
//            }
//
//            override fun onPageSelected(p0: Int) {
//            }
//
//        } )
//
//    }
    private val paintStroke = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }
    private val paintFill = Paint().apply {
        color = Color.GREEN
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    private val paintGrayFill = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val ovalRectF = RectF()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        horizontalDrawArea = width - horizontalPadding
        verticalDrawArea = height - verticalPadding
        val totalCircleSpace = totalCount + (totalCount - 1)
        circleDiameter = horizontalDrawArea / totalCircleSpace
        circleDefaultRadius = circleDiameter / 2
        circleAnimatingRadius = circleDefaultRadius
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (totalCount > 0) {

            for (i in 0 until totalCount) {
                val lengthOfPreCircles = circleDiameter * i
                val lengthOfPreLines = circleDiameter * i
                val cx = lengthOfPreCircles + lengthOfPreLines + circleAnimatingRadius + (horizontalPadding / 2)


                val top = (height / 2) - 10F
                val bottom = (height / 2) + 10F
                val left = cx - circleDefaultRadius.toFloat()
                val right = cx + circleDefaultRadius.toFloat()
                ovalRectF.left = left
                ovalRectF.right = right
                ovalRectF.bottom = bottom
                ovalRectF.top = top

                val ovalPaint = if (selectedPage == i){
                    paintFill
                }else{
                    paintGrayFill
                }



                canvas.drawOval(ovalRectF, ovalPaint)
            }
        }

    }

    public fun setSelectedPage(selected: Int) {
        lastSelectedPage = selectedPage
        selectedPage = selected

        val radiusHolder = PropertyValuesHolder.ofInt(RADIUS_VALUE_ANIMATOR, 0, circleDefaultRadius)
        val alphaHolder = PropertyValuesHolder.ofFloat(ALPHA_VALUE_ANIMATOR, 0F, 1F)

        val animator = ValueAnimator()
        animator.setValues(radiusHolder, alphaHolder)
        animator.duration = 200
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener { animation ->
            val radius = animation.getAnimatedValue(RADIUS_VALUE_ANIMATOR) as Int
            val alpha = animation.getAnimatedValue(ALPHA_VALUE_ANIMATOR) as Float
        }
        animator.start()
        invalidate()
    }

    public fun setPageAnimated(selected: Int) {
        val animator = ValueAnimator.ofInt(0, circleDefaultRadius)
        animator.duration = 1000
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            circleAnimatingRadius = animation.animatedValue as Int
            invalidate()
        }
        animator.start()
    }
}
