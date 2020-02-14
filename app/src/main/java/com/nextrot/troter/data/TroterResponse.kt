package com.nextrot.troter.data

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

// TODO: 이것들 interceptor 에서 body 만 빼낼 수 있는지 고민 필요
@SuppressLint("ParcelCreator")
data class TroterResponse(
    @SerializedName("statusCode")
    val statusCode: Int? = null,
    @SerializedName("statusMsg")
    val statusMsg: String? = null,
    @SerializedName("data")
    val data: Any? = null
)

const val RESPONSE_SUCCESS = 200