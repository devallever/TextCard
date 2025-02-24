package com.allever.android.card.text.pic.text.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allever.android.card.text.pic.text.helper.ActivityHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityHelper.startActivity(this, EditActivity::class.java)
        finish()
    }
}