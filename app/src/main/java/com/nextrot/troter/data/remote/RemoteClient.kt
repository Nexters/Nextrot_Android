package com.nextrot.troter.data.remote

import com.nextrot.troter.data.Singers
import com.nextrot.troter.data.Songs
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteClient {
    @GET("/api/v1/singers/list")
    suspend fun getSingers(): Singers

    @GET("/api/v1/songs")
    suspend fun getSongsOfSinger(@Query("singerId") singerId: String): Songs
}