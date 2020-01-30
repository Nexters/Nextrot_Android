package com.nextrot.troter.data

import android.content.Context
import com.google.gson.Gson
import com.nextrot.troter.R
import com.nextrot.troter.data.remote.RemoteClient
import java.lang.Exception

interface VideoRepository {
    suspend fun search(query: String): SearchResult?
}

class RemoteVideoRepository(private val remoteClient: RemoteClient) : VideoRepository {
    override suspend fun search(query: String): SearchResult? {
        return remoteClient.search(query)
    }
}

class FakeVideoRepository(private val context: Context): VideoRepository {
    override suspend fun search(query: String): SearchResult? {
        val sampleJson = context.resources.getString(R.string.popular_sample)
        return Gson().fromJson(sampleJson, SearchResult::class.java)
    }
}