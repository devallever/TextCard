package com.text.card

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.text.card.core.TemplateManager

class App: Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        context = this
        super.onCreate()
        TemplateManager.initData()
    }
}