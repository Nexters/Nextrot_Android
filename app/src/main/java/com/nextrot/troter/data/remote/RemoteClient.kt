package com.nextrot.troter.data.remote

import com.nextrot.troter.data.Banner
import com.nextrot.troter.data.Singer
import com.nextrot.troter.data.Song
import com.nextrot.troter.data.Video
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteClient {
    @GET("/api/v1/songs/favorite")
    suspend fun getPopular(
        @Query("field") field: String,
        @Query("limit") limit: Int
    ): List<Song>

    @GET("/api/v1/singers/list")
    suspend fun getSingers(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Singer>

    @GET("/api/v1/songs")
    suspend fun getSongsOfSinger(
        @Query("singerId") singerId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Song>

    @GET("/api/v1/videos")
    suspend fun getVideosOfSong(
        @Query("songId"
    ) songId: String): List<String>

    @GET("api/v1/banner/list")
    suspend fun getBanners(): List<Banner>

    @GET("/api/v1/banner/data")
    suspend fun getBannerDetail(@Query("bannerId") bannerId: String): List<Song>
}