package com.nextrot.troter.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nextrot.troter.data.remote.RemoteClient

interface VideoRepository {
    suspend fun popular(): List<Song>
    suspend fun singers(): List<Singer>
    suspend fun songsOfSinger(singerId: String): List<Song>
    suspend fun recentPlaylist(): List<Song>
}

class RemoteVideoRepository(private val remoteClient: RemoteClient) : VideoRepository {
    override suspend fun popular(): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun singers(): List<Singer> {
        return remoteClient.getSingers()
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        return remoteClient.getSongsOfSinger(singerId)
    }

    override suspend fun recentPlaylist(): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// TODO: 캐싱 정책 정해지면 구현 필요
class LocalVideoRepository(private val sharedPreferences: SharedPreferences) {

}


class TroterVideoRepository(private val remote: RemoteVideoRepository, private val local: LocalVideoRepository) {

}

class FakeVideoRepository: VideoRepository {
    override suspend fun singers(): List<Singer> {
        try {
            val sampleJson = Gson().fromJson<Array<Singer>>("""
                    [
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
                """, Array<Singer>::class.java)
            return sampleJson.toList()
        } catch (e: Exception) {
            throw e
        }

    }

    override suspend fun popular(): List<Song> {
        try {
            val sampleJson = Gson().fromJson<Array<Song>>("""
                    [
                        {
                            "id": "32f3156c-ed37-40dc-af96-1431",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "testName",
                            "lyrics": "testLyrics",
                            "like": 0,
                            "view": 0,
                            "video": hrXG7ZbHsTI,
                            "createdAt": 1580371733877,
                            "updatedAt": 1580385272894
                        },
                        {
                            "id": "36483ebb-ec5b-442f-9487-11",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "name2",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": cCRH7wqV6Rw,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        },
                        {
                            "id": "36483ebb-ec5b-442f-345t-1c785370b56d",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "hello",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": fqSkgFClhVE,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        }
                    ]
                """, Array<Song>::class.java)
            return sampleJson.toList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        try {
            val sampleJson = Gson().fromJson<Array<Song>>("""
                    [
                        {
                            "id": "32f3156c-ed37-40dc-af96-1431",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "testName",
                            "lyrics": "testLyrics",
                            "like": 0,
                            "view": 0,
                            "video": hrXG7ZbHsTI,
                            "createdAt": 1580371733877,
                            "updatedAt": 1580385272894
                        },
                        {
                            "id": "36483ebb-ec5b-442f-9487-11",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "name2",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": cCRH7wqV6Rw,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        },
                        {
                            "id": "36483ebb-ec5b-442f-345t-1c785370b56d",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "hello",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": fqSkgFClhVE,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        }
                    ]
                """, Array<Song>::class.java)
            return sampleJson.toList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun recentPlaylist(): List<Song> {
        try {
            val sampleJson = Gson().fromJson<Array<Song>>("""
                     [
                        {
                            "id": "32f3156c-ed37-40dc-af96-1431",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "최근 재생 1",
                            "lyrics": "testLyrics",
                            "like": 0,
                            "view": 0,
                            "video": hrXG7ZbHsTI,
                            "createdAt": 1580371733877,
                            "updatedAt": 1580385272894
                        },
                        {
                            "id": "36483ebb-ec5b-442f-9487-11",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "최근 재생 2",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": cCRH7wqV6Rw,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        },
                        {
                            "id": "36483ebb-ec5b-442f-345t-1c785370b56d",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "최근 재생 3",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": fqSkgFClhVE,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        }
                    ]
                """, Array<Song>::class.java)
            return sampleJson.toList()
        } catch (e: Exception) {
            throw e
        }
    }
}