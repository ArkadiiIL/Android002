package com.arkadii.android002.utils

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import android.view.WindowMetrics

object ImageSizeUtil {
    fun getImageSizeForScreen(context: Context): String {
        val screenWidth = getScreenWidth(context)
        return when {
            screenWidth > 1000 -> "w342"
            screenWidth > 600 -> "w154"
            else -> "w92"
        }
    }

    fun getPlaceholderSizeForScreen(context: Context): String {
        val screenWidth = getScreenWidth(context)
        return when {
            screenWidth > 1000 -> "342x513"
            screenWidth > 600 -> "154x231"
            else -> "92x138"
        }
    }

    private fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics: WindowMetrics = windowManager.currentWindowMetrics
            metrics.bounds.width()
        } else {
            val size = Point()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getSize(size)
            size.x
        }
    }
}