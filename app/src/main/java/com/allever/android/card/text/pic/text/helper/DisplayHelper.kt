package com.allever.android.card.text.pic.text.helper

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.allever.android.card.text.pic.text.App

/**
 * Created by allever on 17-8-5.
 * 单位转换工具类
 */

@SuppressLint("StaticFieldLeak")
object DisplayHelper {

    private const val STATUS_BAR_HEIGHT = "status_bar_height"
    private const val NAVIGATION_BAR_HEIGHT = "navigation_bar_height"

    private var context: Context = App.context

    private fun init(context: Context) {
        DisplayHelper.context = context.applicationContext
    }

    /**
     * 将px转换成dip或dp
     * @param context
     * @param px 像素值
     * @density 转换比例 例如1dp = 3px, density值就是3
     *
     * 320x480      mdpi    1dp = 1px   density = 1
     * 480x800      hdpi    1dp = 1.5px density = 1.5
     * 720x1280     xhdpi   1dp = 2px   density = 2
     * 1080x1920    xxhdpi  1dp = 3px   density = 3
     * 1440x2560    xxxhdpi 1dp = 4px   density = 4
     *
     * @result 返回dip或dp值
     */
    fun px2dip(px: Int): Int {
        val density = context.resources.displayMetrics.density
        return (px / density + 0.5f).toInt()
    }

    fun dip2px(dip: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dip * density + 0.5f).toInt()
    }

    fun dip2px(dip: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dip * density + 0.5f).toInt()
    }

    fun px2sp(px: Int): Int {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return (px / scaledDensity + 0.5f).toInt()
    }

    fun sp2px(sp: Int): Int {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return (sp * scaledDensity + 0.5f).toInt()
    }

    fun getDeviceDensity(): Float {
        return context.resources.displayMetrics.density
    }

    fun getDisplayMetrics(): DisplayMetrics {
        return context.resources.displayMetrics
    }

    fun getScreenWidth(): Int {
        return getDisplayMetrics().widthPixels
    }

    fun getScreenHeight(): Int {
        return getDisplayMetrics().heightPixels
    }

    /**
     * 获取屏幕高度,包括状态栏
     */
    @SuppressLint("ObsoleteSdkInt")
    fun getFullScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val dm = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(dm)
        } else {
            display.getMetrics(dm)
        }
        return dm.heightPixels
    }

    private fun getScreenRealMetrics(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        val wm = context.getSystemService(Service.WINDOW_SERVICE) as? WindowManager
        wm?.defaultDisplay?.getRealMetrics(displayMetrics)
        return displayMetrics
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int =
        getXBarHeight(context, STATUS_BAR_HEIGHT)

    /**
     * 获取导航栏栏高度
     */

    fun getNavigationBarHeight(context: Context): Int =
        getXBarHeight(context, NAVIGATION_BAR_HEIGHT)

    private fun getXBarHeight(context: Context, parameterName: String): Int {
        var height = 0
        val resourceId: Int =
            context.resources.getIdentifier(parameterName, "dimen", "android")
        if (resourceId > 0) {
            height = context.resources.getDimensionPixelSize(resourceId)
        }

        return height
    }

    /**
     * 设置全屏显示
     */
    fun setFullScreen(window: Window) {
        // 全屏显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}
