package com.nextrot.troter.data


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Songs(
    @SerializedName("data")
    val `data`: List<Song>
) : Parcelable, TroterResponse()