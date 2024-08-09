package com.text.card.helper

import android.widget.Toast
import com.text.card.App

fun toast(msg: String) {
    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show()
}