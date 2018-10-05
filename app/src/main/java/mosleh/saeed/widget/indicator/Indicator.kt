package mosleh.saeed.widget.indicator

import android.animation.ArgbEvaluator
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
import android.view.animation.LinearInterpolator

class Indicator(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    init {

    }

    private var totalCount = 5

    private var verticalPadding = 20
    private var horizontalPadding = 20

    private var horizontalDrawArea: Int = 0
    private var verticalDrawArea: Int = 0

    private var circleDiameter: Int = 0
    private var circleDefaultRadius: Int = 0

    private var lastSelectedPage: Int = 0
    private var selectedPage: Int = 0

    private val REVERSE_COLOR_VALUE_ANIMATOR = "reverse_color_value_animator"
    private val COLOR_VALUE_ANIMATOR = "color_value_animator"
    private val ROTATE_VALUE_ANIMATOR = "rotate_value_animator"


    private var reveseColor = Color.WHITE
    private var newColor = Color.GREEN
    private var rotate = 0F
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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (totalCount > 0) {

            for (i in 0 until totalCount) {
                val lengthOfPreCircles = circleDiameter * i
                val lengthOfPreLines = circleDiameter * i
                val cx = lengthOfPreCircles + lengthOfPreLines + circleDefaultRadius + (horizontalPadding / 2)

                val top = (height / 2) - 10F
                val bottom = (height / 2) + 10F
                val left = cx - circleDefaultRadius.toFloat()
                val right = cx + circleDefaultRadius.toFloat()
                ovalRectF.left = left
                ovalRectF.right = right
                ovalRectF.bottom = bottom
                ovalRectF.top = top

                val ovalPaint = if (selectedPage == i) {
                    paintFill
                } else {
                    paintGrayFill
                }

                ovalPaint.color = when (i) {
                    selectedPage -> newColor
                    lastSelectedPage -> reveseColor
                    else -> Color.WHITE
                }
                if (selectedPage == i) {
                    canvas.rotate(rotate)
                }else{
                    canvas.rotate(0F)
                }
                canvas.drawOval(ovalRectF, ovalPaint)
            }
        }

    }

    public fun setSelectedPage(selected: Int) {
        lastSelectedPage = selectedPage
        selectedPage = selected

        animateColors()
    }

    public fun animateColors() {
        val colorHolder = PropertyValuesHolder.ofObject(COLOR_VALUE_ANIMATOR, ArgbEvaluator(), Color.WHITE, Color.GREEN)
        val reverseColorHolder = PropertyValuesHolder.ofObject(REVERSE_COLOR_VALUE_ANIMATOR, ArgbEvaluator(), Color.GREEN, Color.WHITE)
        val rotateHolder = PropertyValuesHolder.ofFloat(ROTATE_VALUE_ANIMATOR, 0F, 180F)

        val animator = ValueAnimator()
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.setValues(colorHolder, reverseColorHolder, rotateHolder)
        animator.addUpdateListener { animation ->
            newColor = animation.getAnimatedValue(COLOR_VALUE_ANIMATOR) as Int
            reveseColor = animation.getAnimatedValue(REVERSE_COLOR_VALUE_ANIMATOR) as Int
            rotate = animation.getAnimatedValue(ROTATE_VALUE_ANIMATOR) as Float
            invalidate()
        }
        animator.start()
    }

    public fun setPageAnimated(selected: Int) {
        val animator = ValueAnimator.ofInt(0, circleDefaultRadius)
        animator.duration = 1000
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            //            circleAnimatingRadius = animation.animatedValue as Int
            invalidate()
        }
        animator.start()
    }

}
