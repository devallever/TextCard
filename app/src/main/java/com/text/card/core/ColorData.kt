package com.text.card.core

data class ColorData(
    val name: String,
    val colorValue: MutableList<String>,
    var selected: Boolean = false
) {


    // ColorData("", mutableListOf("#", "#"))
    companion object {
        //template media
        val BLUE_CYAN_GRADIENT =
            ColorData("Blue cyan gradient", mutableListOf("#78dac5", "#60a5f9"))
        val BLUE_PINK_GRADIENT =
            ColorData("Blue pink gradient", mutableListOf("#d2c9d9", "#39befa"))
        val BLUE_LIME_GRADIENT = ColorData("Blue lime gradient", mutableListOf("#a0e59b", "#7fc3ed"))
        val PINK_BLUE_GRADIENT = ColorData("Pink blue gradient", mutableListOf("#39befa", "#d2c9d9"))
        val BLUE_VIOLET_GRADIENT = ColorData("Blue violet gradient", mutableListOf("#b3bffd", "#8b8cf9"))
        val SKY_ORANGE_GRADIENT = ColorData("Sky orange gradient", mutableListOf("#e2d4c5", "#9fcbfd"))
        val PURPLE_YELLOW_GRADIENT = ColorData("Purple yellow gradient", mutableListOf("#f3e7d7", "#dabdff"))
        val PINK_GRADIENT = ColorData("Pink gradient", mutableListOf("#fdbdbd", "#fa8280"))
        val PINK_RED_GRADIENT = ColorData("Pink red gradient", mutableListOf("#f67286", "#f471b5"))
        val RED_ORANGE_GRADIENT = ColorData("Red orange gradient", mutableListOf("#fdab99", "#fdc283"))
        val LIGHT_DARK_ORANGE_GRADIENT =  ColorData("Dark orange gradient", mutableListOf("#fde589", "#fd9641"))
        val LIGHT_BLUE_PURPLE_GRADIENT =  ColorData("Light blue purple gradient", mutableListOf("#c4d8fd", "#d3b4fd"))
        val YELLOW_ORANGE_GRADIENT =  ColorData("Yellow orange gradient", mutableListOf("#FDF19C", "#FEDC63"))
        val GREEN_GRADIENT =  ColorData("Green gradient", mutableListOf("#80EDAF", "#50DB9F"))
        val GRAY_GRADIENT = ColorData("Gray gradient", mutableListOf("#f8f8f8", "#f4f4f4"))

        //pure light
        val CYAN = ColorData("Cyan", mutableListOf("#07b6d1", "#07b6d1"))
        val LIGHT_CYAN = ColorData("Light cyan", mutableListOf("#24d1ec", "#24d1ec"))
        val BLUE = ColorData("Blue", mutableListOf("#3c82f6", "#3c82f6"))
        val LIGHT_BLUE = ColorData("Light blue", mutableListOf("#60A5FA", "#60A5FA"))
        val TEAL = ColorData("Teal", mutableListOf("#15B8A6", "#15B8A6"))
        val GREEN = ColorData("Green", mutableListOf("#22c55d", "#22c55d"))
        val LIGHT_GREEN = ColorData("Light green", mutableListOf("#49DE80", "#49DE80"))
        val EMERALD = ColorData("Emerald", mutableListOf("#12b980", "#12b980"))
        val ROSE = ColorData("Rose", mutableListOf("#F87171", "#F87171"))
        val LIGHT_ROSE = ColorData("Light rose", mutableListOf("#F471B7", "#F471B7"))
        val LIGHT_PURPLE = ColorData("Light purple", mutableListOf("#c084fc", "#c084fc"))
        val ORANGE = ColorData("Orange", mutableListOf("#f97316", "#f97316"))
        val LIGHT_ORANGE = ColorData("Light orange", mutableListOf("#fb923c", "#fb923c"))
        val YELLOW = ColorData("Yellow", mutableListOf("#facc14", "#facc14"))
        val WHITE = ColorData("White", mutableListOf("#e4e4e7", "#e4e4e7"))

        //pure dark
        val DARK_GRAY_RED = ColorData("Gray red", mutableListOf("#7F1C1E", "#7F1C1E"))
        val DARK_GRAY_GREEN = ColorData("Gray green", mutableListOf("#14532D", "#14532D"))
        val DARK_GRAY_VIOLET = ColorData("Gray violit", mutableListOf("#250f4c", "#250f4c"))
        val DARK_GRAY_INDIGO = ColorData("Gray indigo", mutableListOf("#181637", "#181637"))
        val DARK_GRAY_BLUE = ColorData("Gray blue", mutableListOf("#172554", "#172554"))
        val DARK_GRAY_CYAN = ColorData("Gray cyan", mutableListOf("#093344", "#093344"))
        val DARK_DARK_CYAN = ColorData("Dark cyan", mutableListOf("#0a191f", "#0a191f"))
        val DARK_DARK_INDIGO = ColorData("Dark indigo", mutableListOf("#07060f", "#07060f"))
        val DARK_DARK_GREEN = ColorData("Dark green", mutableListOf("#021b0e", "#021b0e"))
        val DARK_DARK_BLUE = ColorData("Dark blue", mutableListOf("#010512", "#010512"))
        val DARK_DARK_VIOLET = ColorData("Dark violet", mutableListOf("#0d0816", "#0d0816"))
        val DARK_LIGHT_WARM_GRAY = ColorData("Light warm gray", mutableListOf("#262626", "#262626"))
        val DARK_LIGHT_COLD_GRAY = ColorData("Light cold gray", mutableListOf("#1e293b", "#1e293b"))
        val DARK_BLACK = ColorData("Black", mutableListOf("#0a0a0a", "#0a0a0a"))

        //ColorData("", mutableListOf("#", "#"))


        val DARK_DARK_BLUE_GRADIENT =
            ColorData("Dark blue gradient", mutableListOf("#14214d", "#061735"))
        val DARK_DARK_INDIGO_GRADIENT =
            ColorData("Dark indigo gradient", mutableListOf("#272767", "#100d21"))
        val DARK_DARK_SKY_GRADIENT = ColorData("Dark sky gradient", mutableListOf("#0c405e", "#071420"))
        val DARK_DARK_CYAN_GRADIENT = ColorData("Dark cyan gradient", mutableListOf("#0f3346", "#071320"))
        val DARK_DARK_VIOLET_GRADIENT =ColorData("Dark violet gradient", mutableListOf("#40197d", "#150a25"))
        val DARK_DARK_FUSHSIA_GRADIENT = ColorData("Dark fushsia gradient", mutableListOf("#591868", "#1f0d3d"))
        val DARK_DARK_ROSE_GRADIENT = ColorData("Dark rose gradient", mutableListOf("#70102c", "#1d080c"))
        val DARK_DARK_EMERALD_GRADIENT = ColorData("Dark emerald gradient", mutableListOf("#0d4224", "#03160c"))
        val DARK_DARK_GRAY_GRADIENT = ColorData("Dark gray gradient", mutableListOf("#1a2432", "#0d1221"))
        val DARK_DARK_SLATE_GRADIENT = ColorData("Dark slate gradient", mutableListOf("#192437", "#0d1025"))
        val DARK_DARK_NEUTRAL_GRADIENT = ColorData("Dark neutral gradient", mutableListOf("#343434", "#0f0f0f"))
        val DARK_DARK_STONE_GRADIENT = ColorData("Dark stone gradient", mutableListOf("#44423d", "#191412"))

        // ColorData("", mutableListOf("#", "#"))
    }
}