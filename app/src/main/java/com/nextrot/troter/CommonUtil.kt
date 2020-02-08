package com.nextrot.troter

import android.util.DisplayMetrics
import android.util.TypedValue

class CommonUtil {
    companion object {
        @JvmStatic
        fun toDP(value: Int, metrics: DisplayMetrics): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics)
        }
    }
}