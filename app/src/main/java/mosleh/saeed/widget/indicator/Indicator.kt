package mosleh.saeed.widget.indicator

import android.animation.*
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator

class Indicator(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    init {

    }

    val items = ArrayList<IndicatorModel>()
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

    private var selectedXTranslation = 0f
    private var selectedYTranslation = 0f
    private var unselectedCurrentTranslationX = 0f
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
        initItems(circleDiameter)
    }

    private fun initItems(diameter: Int) {
        for (i in 0..4) {
            if (i == 0) {
                items.add(IndicatorModel(paintFill, true, diameter.toFloat()))
            } else {
                items.add(IndicatorModel(paintGrayFill, false, diameter.toFloat()))

            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (totalCount > 0) {


            for (pos in items.indices) {
                val it = items[pos]
                val lengthOfPreCircles = circleDiameter * pos
                val lengthOfPreLines = circleDiameter * pos
                val cx = lengthOfPreCircles + lengthOfPreLines + circleDefaultRadius + (horizontalPadding / 2)
                val top = (height / 2) - 10F
                val bottom = (height / 2) + 10F
                val left = cx - it.radius
                val right = cx + it.radius
                ovalRectF.left = left
                ovalRectF.right = right
                ovalRectF.bottom = bottom
                ovalRectF.top = top
                if (it.isSelected) {
                    ovalRectF.left = left + selectedXTranslation
                    ovalRectF.right = right + selectedXTranslation
                    ovalRectF.bottom = bottom + selectedYTranslation
                    ovalRectF.top = top + selectedYTranslation

                }
                if (lastSelectedPage < selectedPage) {
                    if (pos in (lastSelectedPage + 1)..(selectedPage)) {
                        ovalRectF.left = left - unselectedCurrentTranslationX
                        ovalRectF.right = right - unselectedCurrentTranslationX
                    }
                } else {
                    if (pos in (selectedPage)..(lastSelectedPage - 1)) {
                        ovalRectF.left = left + unselectedCurrentTranslationX
                        ovalRectF.right = right + unselectedCurrentTranslationX
                    }
                }

                canvas.drawOval(ovalRectF, it.paint)
            }
//            for (i in 0 until totalCount) {
//
//
////                ovalPaint.color = when (i) {
////                    selectedPage -> newColor
////                    lastSelectedPage -> reveseColor
////                    else -> Color.WHITE
////                }
////                if (selectedPage == i) {
////                    canvas.rotate(rotate)
////                }else{
////                    canvas.rotate(0F)
////                }
////                canvas.drawOval(ovalRectF, ovalPaint)
//            }
        }

    }

    fun setSelectedPage(selected: Int) {
        if (selected != lastSelectedPage) {
            lastSelectedPage = selectedPage
            selectedPage = selected
            Log.i("indicator", "current: $selected, Last: $lastSelectedPage ")
            animateToCurrentPos()
        }
    }

    private fun animateToCurrentPos() {

        val distance = selectedPage - lastSelectedPage
        val animatorXValue = PropertyValuesHolder.ofFloat("translate_x", 0F, circleDiameter * 2 * distance.toFloat())
        val animatorYValue = PropertyValuesHolder.ofFloat("translate_y", 0F, circleDiameter.toFloat() * 2)
        val rotation = PropertyValuesHolder.ofFloat("rotate", 0F, 180f)

        val animator = ValueAnimator()
        animator.setValues(animatorXValue, animatorYValue)
        animator.duration = 800
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            selectedXTranslation = it.getAnimatedValue("translate_x") as Float
            unselectedCurrentTranslationX = (it.getAnimatedValue("translate_x") as Float) / distance
            val yValue = it.getAnimatedValue("translate_y") as Float
            val tempYTranslate = if (yValue < circleDiameter)
                (it.getAnimatedValue("translate_y") as Float) % circleDiameter
            else
                circleDiameter - ((it.getAnimatedValue("translate_y") as Float) % circleDiameter)
            if (tempYTranslate < circleDiameter * 0.7)
                selectedYTranslation = tempYTranslate
            invalidate()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                selectedXTranslation = 0f
                selectedYTranslation = 0f
                unselectedCurrentTranslationX = 0f
                items[lastSelectedPage].isSelected = false
                items[lastSelectedPage].paint = paintGrayFill
                items[selectedPage].isSelected = true
                items[selectedPage].paint = paintFill
                invalidate()
            }
        })
        animator.start()
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
