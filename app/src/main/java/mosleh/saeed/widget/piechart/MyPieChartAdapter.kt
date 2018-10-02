package mosleh.saeed.widget.piechart

import android.graphics.Paint


class MyPieChartAdapter : PieChartAdapter {


    constructor(data: List<PieDataInterface>, max: Float) : super(data, max) {
        processDrawData()
    }

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
