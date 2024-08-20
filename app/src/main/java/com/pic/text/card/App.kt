package com.pic.text.card

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.pic.text.card.R
import com.pic.text.card.core.TemplateManager
import com.pic.text.card.core.TextCardCore
import com.pic.text.card.helper.StoreManager

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
        handleFirstOpen()
        TemplateManager.initData()
    }

    private fun handleFirstOpen() {
        val firstOpen = StoreManager.getBoolean("first open", true)
        if (firstOpen) {
            TextCardCore.cardData.title = getString(R.string.default_title)
            TextCardCore.cardData.text = getString(R.string.default_text)
            TextCardCore.cardData.author = getString(R.string.default_author)
            TextCardCore.saveCardData()
            StoreManager.putBoolean("first open", false)
        }
    }
}