package com.nextrot.troter.data

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import com.google.gson.annotations.Expose

import kotlinx.android.parcel.Parcelize

import com.google.gson.annotations.SerializedName
@SuppressLint("ParcelCreator")
@Parcelize
data class SearchResult(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo,
    @SerializedName("regionCode")
    val regionCode: String
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Item(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: Id,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("snippet")
    val snippet: Snippet,
    @Expose
    var selected: Boolean = false
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Id(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("videoId")
    val videoId: String
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Snippet(
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("channelTitle")
    val channelTitle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("liveBroadcastContent")
    val liveBroadcastContent: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    @SerializedName("title")
    val title: String
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Thumbnails(
    @SerializedName("default")
    val default: Default,
    @SerializedName("high")
    val high: High,
    @SerializedName("medium")
    val medium: Medium
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Default(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class High(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Medium(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class PageInfo(
    @SerializedName("resultsPerPage")
    val resultsPerPage: Int,
    @SerializedName("totalResults")
    val totalResults: Int
) : Parcelable