package com.kingadel.piechart

data class PieData(private val color: Int, private val amount: Float) : PieDataInterface {
    override fun getAmount() = amount
    override fun getColor() = color
}
