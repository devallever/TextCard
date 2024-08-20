package com.pic.text.card.helper

import android.widget.Toast
import com.pic.text.card.App

fun toast(msg: String) {
    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show()
}