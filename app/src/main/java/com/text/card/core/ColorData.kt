package com.text.card.core

data class ColorData(
    val name: String,
    val colorValue: MutableList<String>,
    var selected: Boolean = false
) {

    companion object {
        //template media
        val BLUE_CYAN_GRADIENT =
            ColorData("Blue cyan gradient", mutableListOf("#78dac5", "#60a5f9"))
        val BLUE_PINK_GRADIENT =
            ColorData("Blue pink gradient", mutableListOf("#d2c9d9", "#39befa"))

        val DARK_BLUE_GRADIENT =
            ColorData("Dark blue gradient", mutableListOf("#14214d", "#061735"))
        val DARK_INDIGO_GRADIENT =
            ColorData("Dark indigo gradient", mutableListOf("#272767", "#100d21"))
    }
}