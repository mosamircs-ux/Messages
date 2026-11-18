package org.fossify.messages.helpers

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import org.fossify.commons.extensions.getContrastColor
import org.fossify.commons.extensions.getProperBackgroundColor

object PulseThemeHelper {
    private fun Context.dp(value: Float): Float = value * resources.displayMetrics.density

    fun isDarkThemeActive(context: Context): Boolean {
        return context.getProperBackgroundColor().getContrastColor() == Color.WHITE
    }

    fun createConversationCardDrawable(context: Context, primaryColor: Int): Drawable {
        val isDark = isDarkThemeActive(context)
        val radius = context.dp(18f)
        val strokeWidth = context.dp(1f).toInt().coerceAtLeast(1)
        val selectedStrokeWidth = context.dp(1.5f).toInt().coerceAtLeast(2)

        val normalBg = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = radius
            setColor(if (isDark) Color.parseColor("#141D2F") else Color.parseColor("#F8FAFF"))
            setStroke(strokeWidth, if (isDark) Color.parseColor("#243352") else Color.parseColor("#DCE6FF"))
        }

        val selectedBg = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = radius
            setColor(if (isDark) Color.parseColor("#2A2563EB") else Color.parseColor("#1F2563EB"))
            setStroke(selectedStrokeWidth, primaryColor)
        }

        val stateList = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_selected), selectedBg)
            addState(intArrayOf(), normalBg)
        }

        val rippleColor = ColorStateList.valueOf(
            if (isDark) Color.parseColor("#333B82F6") else Color.parseColor("#242563EB")
        )
        return RippleDrawable(rippleColor, stateList, null)
    }

    fun createReceivedBubbleDrawable(context: Context): Drawable {
        val isDark = isDarkThemeActive(context)
        val big = context.dp(20f)
        val small = context.dp(6f)
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadii = floatArrayOf(big, big, big, big, big, big, small, small)
            setColor(if (isDark) Color.parseColor("#1E293B") else Color.parseColor("#EEF2FF"))
        }
    }

    fun createComposerPillDrawable(context: Context): Drawable {
        val isDark = isDarkThemeActive(context)
        val radius = context.dp(26f)
        val strokeWidth = context.dp(1f).toInt().coerceAtLeast(1)
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = radius
            setColor(if (isDark) Color.parseColor("#141D2F") else Color.parseColor("#EFF4FF"))
            setStroke(strokeWidth, if (isDark) Color.parseColor("#2B3B59") else Color.parseColor("#C7D8FE"))
        }
    }
}
