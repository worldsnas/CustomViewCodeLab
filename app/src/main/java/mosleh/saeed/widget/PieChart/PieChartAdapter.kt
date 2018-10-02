package mosleh.saeed.widget.PieChart

import android.content.res.Resources
import android.graphics.Paint
import java.util.*

abstract class PieChartAdapter(val data: List<PieDataInterface>, val max : Float , val paintStyle : Paint.Style = Paint.Style.STROKE , stroke : Int = 10) {
    val strokeSize by lazy {
        stroke.applyDpToPixels()
    }
    val offset by lazy {
        strokeSize
    }
    val drawData = LinkedList<DrawData>()
    abstract fun processDrawData()
}
fun Int.applyDpToPixels(): Float {
    return Resources.getSystem().displayMetrics.density * this.toFloat()
}
