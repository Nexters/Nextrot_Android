package com.nextrot.troter.data


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Video(
    val createdAt: String,
    val id: String,
    val key: String,
    val like: Int,
    val updatedAt: String,
    val view: Int
) : Parcelable