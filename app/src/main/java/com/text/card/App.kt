package com.text.card

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.text.card.core.TemplateManager

class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        fun getColor(colorResId: Int): Int {
            return ContextCompat.getColor(context, colorResId)
        }
    }

    override fun onCreate() {
        context = this
        super.onCreate()
        TemplateManager.initData()
    }
}