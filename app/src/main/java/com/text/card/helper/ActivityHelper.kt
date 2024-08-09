package com.text.card.helper

import android.app.Activity
import android.content.Intent

object ActivityHelper {
    fun startActivity(
        context: Activity,
        clz: Class<*>
    ) {
        val intent = Intent(context, clz)
        context.startActivity(intent)
    }

}