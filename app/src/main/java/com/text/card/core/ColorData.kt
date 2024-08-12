package com.text.card.core

data class ColorData(
    val name: String,
    val colorValue: MutableList<String>,
    var selected: Boolean = false
) {
}