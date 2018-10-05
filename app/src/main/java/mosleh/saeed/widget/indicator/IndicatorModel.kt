package mosleh.saeed.widget.indicator

import android.graphics.Paint

data class IndicatorModel(var paint: Paint , var isSelected: Boolean , val diameter : Float){
    val radius by lazy {
        diameter/2
    }
}
