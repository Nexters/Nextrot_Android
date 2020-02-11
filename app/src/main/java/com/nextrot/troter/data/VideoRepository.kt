package com.nextrot.troter.data

import android.content.Context
import com.google.gson.Gson
import com.nextrot.troter.data.remote.RemoteClient
import java.lang.Exception

interface VideoRepository {
    suspend fun popular(): List<Song>
    suspend fun singers(): List<Singer>
    suspend fun songsOfSinger(singerId: String): List<Song>
}

class RemoteVideoRepository(private val remoteClient: RemoteClient) : VideoRepository {
    override suspend fun popular(): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun singers(): List<Singer> {
        val result = remoteClient.getSingers()
        if (result.statusCode == 200) {
            return remoteClient.getSingers().data
        }
        return listOf()
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        val result = remoteClient.getSongsOfSinger(singerId)
        if (result.statusCode == 200) {
            return remoteClient.getSongsOfSinger(singerId).data
        }
        return listOf()
    }
}

class FakeVideoRepository: VideoRepository {
    override suspend fun singers(): List<Singer> {
        try {
            val sampleJson = Gson().fromJson<Singers>("""
                    {
                        "statusCode": 200,
                        "statusMsg": "success",
                        "data": [
                            {
                                "id": "5e328f15b7e4937f52c67679",
                                "name": "Gray",
                                "songs": null,
                                "like": 0,
                                "createdAt": 1580371733877,
                                "updatedAt": 1580385339058
                            },
                            {
                                "id": "5e328f15b7e4937f52c67679",
                                "name": "HoYoung",
                                "songs": null,
                                "like": 0,
                                "createdAt": 1580371733877,
                                "updatedAt": 1580385339058
                            },
                            {
                                "id": "5e32c8cc50823b60f80758e5",
                                "name": "SangHo",
                                "songs": null,
                                "like": 0,
                                "createdAt": 1580386507954,
                                "updatedAt": 1580389220910
                            }
                        ]
                    }
                """, Singers::class.java)
            return sampleJson.data
        } catch (e: Exception) {
            throw e
        }

    }

    override suspend fun popular(): List<Song> {
        return listOf()
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        try {
            val sampleJson = Gson().fromJson<Songs>("""
                    {
                        "statusCode": 200,
                        "statusMsg": "success",
                        "data": [
                            {
                                "id": "32f3156c-ed37-40dc-af96-dcbf53a84a58",
                                "singerId": "5e328f15b7e4937f52c67679",
                                "name": "testName",
                                "lyrics": "testLyrics",
                                "like": 0,
                                "view": 0,
                                "video": null,
                                "createdAt": 1580371733877,
                                "updatedAt": 1580385272894
                            },
                            {
                                "id": "36483ebb-ec5b-442f-9487-1c785370b56d",
                                "singerId": "5e328f15b7e4937f52c67679",
                                "name": "Music2",
                                "lyrics": "Musice2Lyrics",
                                "like": 0,
                                "view": 0,
                                "video": null,
                                "createdAt": 1580385157216,
                                "updatedAt": 1580385339058
                            }
                        ]
                    }
                """, Songs::class.java)
            return sampleJson.data
        } catch (e: Exception) {
            throw e
        }
    }
}