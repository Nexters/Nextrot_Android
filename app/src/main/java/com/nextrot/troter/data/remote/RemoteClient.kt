package com.nextrot.troter.data.remote

import com.nextrot.troter.data.Singer
import com.nextrot.troter.data.Song
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteClient {
    @GET("/api/v1/singers/list")
    suspend fun getSingers(): List<Singer>

    @GET("/api/v1/songs")
    suspend fun getSongsOfSinger(@Query("singerId") singerId: String): List<Song>
}