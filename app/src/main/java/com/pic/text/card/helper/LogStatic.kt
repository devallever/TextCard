package com.pic.text.card.helper

import android.util.Log
import com.pic.text.card.BuildConfig

fun log(msg: String) {
    if (BuildConfig.DEBUG) {
        Log.d("TextCardApp", msg)
    }
}