package mosleh.saeed.widget.PieChart

import android.graphics.Paint
import java.util.*


class MyPieChartAdapter : PieChartAdapter {


    constructor(data: List<PieDataInterface>, max: Float, paintStyle: Paint.Style, stroke: Int) : super(data, max, paintStyle, stroke) {
        processDrawData()
    }
    override fun processDrawData() {
        var lastAngle = -90f
        data.forEach {
            val startingAngle = lastAngle
            val drawingAngle = (it.getAmount() / max) * 360
            lastAngle += drawingAngle
            val paint = createPaint(it.getColor())
            drawData.add(DrawData(startingAngle, drawingAngle, paint))
        }
    }


    fun createPaint(color: Int): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = paintStyle
            strokeWidth = strokeSize
            this.color = color
        }
    }
}
