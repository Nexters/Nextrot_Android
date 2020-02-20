package com.nextrot.troter.data


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Banner(
    @SerializedName("actionType")
    val actionType: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("updatedAt")
    val updatedAt: String
) : Parcelable