package com.pic.text.card.helper


fun Any.toJson(): String {
    return GsonHelper.toJson(this)
}