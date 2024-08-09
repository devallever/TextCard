package com.text.card.helper

import android.view.View
import android.view.ViewGroup

object ViewHelper {

    fun setMarginTop(view: View, marginTop: Int) {
        view.post {
            val lp = view.layoutParams as ViewGroup.MarginLayoutParams
            lp.topMargin = marginTop
            view.layoutParams = lp
        }
    }

}