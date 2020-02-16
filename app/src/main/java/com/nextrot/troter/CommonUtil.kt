package com.nextrot.troter

import android.content.res.Resources
import android.util.TypedValue

class CommonUtil {
    companion object {
        @JvmStatic
        fun toDP(value: Int): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), Resources.getSystem().displayMetrics)
        }
    }
}