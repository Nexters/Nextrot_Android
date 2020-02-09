package com.nextrot.troter.data

import android.content.Context
import com.google.gson.Gson
import com.nextrot.troter.R
import com.nextrot.troter.data.remote.RemoteClient
import java.lang.Exception

interface VideoRepository {
    suspend fun search(query: String): SearchResult?
    suspend fun popular(): ArrayList<Item>
    suspend fun singers(): ArrayList<String>
    suspend fun songsOfSinger(singer: String): ArrayList<Item>
}

class RemoteVideoRepository(private val remoteClient: RemoteClient) : VideoRepository {
    override suspend fun search(query: String): SearchResult? {
        return remoteClient.search(query)
    }

    override suspend fun popular(): ArrayList<Item> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun singers(): ArrayList<String> {
        return arrayListOf()
    }

    override suspend fun songsOfSinger(singer: String): ArrayList<Item> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class FakeVideoRepository(private val context: Context): VideoRepository {
    override suspend fun search(query: String): SearchResult? {
        val sampleJson = context.resources.getString(R.string.popular_sample)
        return Gson().fromJson(sampleJson, SearchResult::class.java)
    }

    override suspend fun singers(): ArrayList<String> {
        return arrayListOf("송가인", "송가인")
    }

    override suspend fun popular(): ArrayList<Item> {
        val sampleJson = context.resources.getString(R.string.popular_sample)
        return ArrayList(Gson().fromJson(sampleJson, SearchResult::class.java).items)
    }

    override suspend fun songsOfSinger(singer: String): ArrayList<Item> {
        val sampleJson = context.resources.getString(R.string.popular_sample)
        return ArrayList(Gson().fromJson(sampleJson, SearchResult::class.java).items)
    }
}