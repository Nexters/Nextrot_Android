package com.nextrot.troter.data.remote

import com.nextrot.troter.BuildConfig
import com.nextrot.troter.data.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

// TODO: query param 으로 토큰을 전달 하는 것을 http client interceptor 로 위임할 수 있는지 검토 필요
// 테스트용 임시처리 했지만, API 바뀌면 그 때 정리하자.
interface RemoteClient {
    @GET("/youtube/v3/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("key") token: String = BuildConfig.AUTH_TOKEN,
        @Query("part") part: String = "snippet",
        @Query("type") type: String = "video"
    ): SearchResult
}