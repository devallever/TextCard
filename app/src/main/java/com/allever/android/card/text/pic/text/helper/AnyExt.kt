package com.allever.android.card.text.pic.text.helper


fun Any.toJson(): String {
    return GsonHelper.toJson(this)
}