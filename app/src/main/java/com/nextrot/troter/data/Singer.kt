package com.nextrot.troter.data


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import java.util.*

@SuppressLint("ParcelCreator")
@Parcelize
data class Singer(
    @SerializedName("createdAt")
    val createdAt: Long,
    @SerializedName("id")
    val id: String,
    @SerializedName("like")
    val like: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("songs")
    val songs: List<Song>,
    @SerializedName("updatedAt")
    val updatedAt: Long
) : Parcelable