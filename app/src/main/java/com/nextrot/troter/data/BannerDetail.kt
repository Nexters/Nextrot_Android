package com.nextrot.troter.data


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import javax.annotation.Nullable

@SuppressLint("ParcelCreator")
@Parcelize
data class BannerDetail(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("like")
    val like: Int,
    @SerializedName("songId")
    @Nullable
    val songId: String?,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("view")
    val view: Int
) : Parcelable