package mosleh.saeed.widget.circleeffect

import android.graphics.Color
import java.util.*


data class Circle(val x: Float, val y: Float, val creationTime : Long){
    val color = Color.rgb(Random().nextInt(100)+154
            ,Random().nextInt(100) + 154
            ,Random().nextInt(100) + 154)
}
