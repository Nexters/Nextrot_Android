package com.nextrot.troter.data


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Song(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String?,
    @SerializedName("songId")
    val songId: String?,
    @SerializedName("singerName")
    val singerName: String?,
    @SerializedName("like")
    val like: Int,
    @SerializedName("lyrics")
    val lyrics: String,
    @SerializedName("name")
    val name: String,
//    @SerializedName("singerId")
//    val singerId: String = "",
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("video")
    val video: List<Video> = listOf(),
    @SerializedName("view")
    val view: Int
) : Parcelable