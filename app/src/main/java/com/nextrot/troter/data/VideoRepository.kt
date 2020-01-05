package com.nextrot.troter.data

import com.nextrot.troter.data.remote.RemoteClient

interface VideoRepository {
    suspend fun search(query: String): SearchResult?
}

class RemoteVideoRepository(private val remoteClient: RemoteClient) : VideoRepository {
    override suspend fun search(query: String): SearchResult? {
        return remoteClient.search(query)
    }
}