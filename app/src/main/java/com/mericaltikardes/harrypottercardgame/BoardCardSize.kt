package com.mericaltikardes.harrypottercardgame


enum class BoardCardSize(val cardSize: Int) {
    FOUR(8), SIXTEEN(24), TWENTFOUR(32);

    fun getWidth(): Int {
        return when (this) {
            FOUR -> 2
            SIXTEEN -> 4
            TWENTFOUR -> 4
        }
    }

    fun getHeight(): Int {
        return cardSize / getWidth()
    }

    fun getCardCount(): Int {
        return cardSize
    }

    fun setCardCount(count: Int) {
        when (count) {
            8 -> FOUR
            24 -> SIXTEEN
            32 -> TWENTFOUR
            else -> throw IllegalArgumentException("Invalid card count: $count")
        }
    }
}