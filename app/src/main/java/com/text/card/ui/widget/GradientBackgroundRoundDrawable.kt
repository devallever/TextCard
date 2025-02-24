package com.text.card.ui.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import androidx.annotation.Nullable
import com.text.card.helper.DisplayHelper

class GradientBackgroundRoundDrawable : GradientDrawable() {
    private val mPaint = Paint()

    override fun draw(canvas: Canvas) {
        canvas.drawRect(getBounds(), mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.setAlpha(alpha)
    }

    override fun setColorFilter(@Nullable colorFilter: ColorFilter?) {
        mPaint.setColorFilter(colorFilter)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    fun setColorList(color: List<String>, width: Float, height: Float) {
        val colorList = mutableListOf<Int>()
        val locationList = mutableListOf<Float>()
        color.map {
            colorList.add(Color.parseColor(it))
            locationList.add(0.5f)
        }
        val shader = LinearGradient(
            width,
            0f,
            width * 0.1f,
            height * 0.95f,
            colorList.toIntArray(),
            null,
            Shader.TileMode.CLAMP
        )
        val r = DisplayHelper.dip2px(20f).toFloat()
//        cornerRadii = floatArrayOf(r,r,r,r,r,r,r,r)
        shape = OVAL
        cornerRadius = 60F
        mPaint.setShader(shader)
    }

    fun setColorListGradient(color: List<String>, width: Float, height: Float) {
        val colorList = mutableListOf<Int>()
        val locationList = mutableListOf<Float>()
        color.map {
            colorList.add(Color.parseColor(it))
            locationList.add(0.5f)
        }
        val shader = LinearGradient(
            width * 0.5f,
            0f,
            width * 0.5f,
            height,
            colorList.toIntArray(),
            null,
            Shader.TileMode.CLAMP
        )
        mPaint.setShader(shader)
    }
}