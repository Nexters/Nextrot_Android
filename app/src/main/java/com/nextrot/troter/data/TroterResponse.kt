package com.nextrot.troter.data

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

@SuppressLint("ParcelCreator")
abstract class TroterResponse {
    @SerializedName("statusCode")
    val statusCode: Int? = null
    @SerializedName("statusMsg")
    val statusMsg: String? = null
}