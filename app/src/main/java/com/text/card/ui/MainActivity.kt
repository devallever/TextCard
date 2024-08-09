package com.text.card.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.text.card.helper.ActivityHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityHelper.startActivity(this, EditActivity::class.java)
        finish()
    }
}