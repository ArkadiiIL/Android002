package com.arkadii.android002.utils

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import android.view.WindowMetrics
import com.arkadii.android002.R

object ImageSizeUtil {
    private const val IMG_URL = "https://image.tmdb.org/t/p/"
    private const val PLACEHOLDER_URL = "https://placehold.co/"
    private const val PLACEHOLDER_TEXT_ATTR = "jpg?text="
    fun getImageUrl(context: Context, posterPath: String?) = if (posterPath != null) {
        val imageSize = getImageSizeForScreen(context)
        "$IMG_URL$imageSize$posterPath"
    } else {
        val imageSize = getPlaceholderSizeForScreen(context)
        val imageText = context.getString(R.string.image_placeholder_text)
        "$PLACEHOLDER_URL$imageSize$PLACEHOLDER_TEXT_ATTR$imageText"
    }

    fun getImageDetailUrl(context: Context, posterPath: String?) = if (posterPath != null) {
        val imageSize = getImageDetailSizeForScreen(context)
        "$IMG_URL$imageSize$posterPath"
    } else {
        val imageSize = getPlaceholderDetailSizeForScreen(context)
        val imageText = context.getString(R.string.image_placeholder_text)
        "$PLACEHOLDER_URL$imageSize$PLACEHOLDER_TEXT_ATTR$imageText"
    }

    private fun getImageSizeForScreen(context: Context): String {
        val screenWidth = getScreenWidth(context)
        return when {
            screenWidth > 1000 -> "w342"
            screenWidth > 600 -> "w154"
            else -> "w92"
        }
    }

    private fun getImageDetailSizeForScreen(context: Context): String {
        val screenWidth = getScreenWidth(context)
        return when {
            screenWidth > 1000 -> "w780"
            screenWidth > 600 -> "w500"
            else -> "w342"
        }
    }

    private fun getPlaceholderSizeForScreen(context: Context): String {
        val screenWidth = getScreenWidth(context)
        return when {
            screenWidth > 1000 -> "342x513"
            screenWidth > 600 -> "154x231"
            else -> "92x138"
        }
    }

    private fun getPlaceholderDetailSizeForScreen(context: Context): String {
        val screenWidth = getScreenWidth(context)
        return when {
            screenWidth > 1000 -> "780x1170"
            screenWidth > 600 -> "500x750"
            else -> "342x513"
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