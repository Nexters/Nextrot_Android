package com.nextrot.troter.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nextrot.troter.PREF_SAVED_SONGS
import com.nextrot.troter.data.remote.RemoteClient

interface VideoRepository {
    suspend fun popular(): List<Song>
    suspend fun singers(): List<Singer>
    suspend fun songsOfSinger(singerId: String): List<Song>
    suspend fun savePlaylist(songs: List<Song>)
    suspend fun getSavedPlaylist(): List<Song>
    suspend fun getVideosOfSong(songId: String): List<String>
    suspend fun getBanners(): List<Banner>
    suspend fun getBannerDetail(bannerId: String): BannerDetail
}

class RemoteVideoRepository(private val remoteClient: RemoteClient) : VideoRepository {
    override suspend fun popular(): List<Song> {
        return remoteClient.getPopular("view", 30)
    }

    override suspend fun singers(): List<Singer> {
        return remoteClient.getSingers(0, 20)
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        return remoteClient.getSongsOfSinger(singerId, 0, 30)
    }

    override suspend fun savePlaylist(songs: List<Song>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getSavedPlaylist(): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getVideosOfSong(songId: String): List<String> {
        return remoteClient.getVideosOfSong(songId)
    }

    override suspend fun getBanners(): List<Banner> {
        return remoteClient.getBanners()
    }

    override suspend fun getBannerDetail(bannerId: String): BannerDetail {
        return remoteClient.getBannerDetail(bannerId)
    }
}

// TODO: 캐싱 정책 정해지면 구현 필요
class LocalVideoRepository(private val sharedPreferences: SharedPreferences): VideoRepository {
    override suspend fun popular(): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun singers(): List<Singer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun savePlaylist(songs: List<Song>) {
        val jsonSongs = Gson().toJson(songs)
        sharedPreferences
            .edit()
            .putString(PREF_SAVED_SONGS, jsonSongs)
            .apply()
    }

    override suspend fun getSavedPlaylist(): List<Song> {
        val jsonSongs = sharedPreferences.getString(PREF_SAVED_SONGS, "[]")
        return Gson().fromJson(jsonSongs, Array<Song>::class.java).toList()
    }

    override suspend fun getVideosOfSong(songId: String): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getBanners(): List<Banner> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getBannerDetail(bannerId: String): BannerDetail {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class TroterVideoRepository(private val remote: RemoteVideoRepository, private val local: LocalVideoRepository) : VideoRepository {
    override suspend fun popular(): List<Song> {
        return remote.popular()
    }

    override suspend fun singers(): List<Singer> {
        return remote.singers()
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        return remote.songsOfSinger(singerId)
    }

    override suspend fun savePlaylist(songs: List<Song>) {
        local.savePlaylist(songs)
    }

    override suspend fun getSavedPlaylist(): List<Song> {
        return local.getSavedPlaylist()
    }

    override suspend fun getVideosOfSong(songId: String): List<String> {
        return remote.getVideosOfSong(songId)
    }

    override suspend fun getBanners(): List<Banner> {
        return remote.getBanners()
    }

    override suspend fun getBannerDetail(bannerId: String): BannerDetail {
        return remote.getBannerDetail(bannerId)
    }
}

class FakeVideoRepository(private val sharedPreferences: SharedPreferences): VideoRepository {
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
                        },
                        {
                            "id": "32f31111156c-ed37-40dc-af96-1431",
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
                            "id": "36483ebb-ec5b-442f-94144487-11",
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
                            "id": "36483ebb-ec5b-442f55-345t-1c785370b56d",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "hello",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": fqSkgFClhVE,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        },
                        {
                            "id": "36111483ebb-ec5b-442f55-345t-1c785gdb370b56d",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "hello",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": fqSkgFClhVE,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        },
                        {
                            "id": "361114sas83ebb-ec5b-442f55-345t-1c785gdb370b56d",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "hello",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": fqSkgFClhVE,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        },
                        {
                            "id": "36111dfbfd483ebb-ec5b-442f55-345t-1c785gdb370b56d",
                            "singerId": "5e328f15b7e4937f52c67679",
                            "name": "hello",
                            "lyrics": "Musice2Lyrics",
                            "like": 0,
                            "view": 0,
                            "video": fqSkgFClhVE,
                            "createdAt": 1580385157216,
                            "updatedAt": 1580385339058
                        },
                        {
                            "id": "361114qw3r83ebb-ec5b-442f55-345t-1c785gdb370b56d",
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

    override suspend fun savePlaylist(songs: List<Song>) {
        val jsonSongs = Gson().toJson(songs)
        sharedPreferences
            .edit()
            .putString(PREF_SAVED_SONGS, jsonSongs)
            .apply()
    }

    override suspend fun getSavedPlaylist(): List<Song> {
        val jsonSongs = sharedPreferences.getString(PREF_SAVED_SONGS, "[]")
        return Gson().fromJson(jsonSongs, Array<Song>::class.java).toList()
    }

    override suspend fun getVideosOfSong(songId: String): List<String> {
        return listOf("fqSkgFClhVE")
    }

    override suspend fun getBanners(): List<Banner> {
        try {
            val sampleJson = Gson().fromJson<Array<Banner>>("""
                    [
                        {
                            "id": "5e47d19ae76841c34af7d616",
                            "imageUrl": "https://dailyissue.s3.ap-northeast-2.amazonaws.com/Banner_0215(1).png",
                            "state": "C",
                            "actionType": 0,
                            "data": null,
                            "createdAt": "2020-01-30T08:11:06.934+0000",
                            "updatedAt": "2020-01-30T08:11:06.934+0000"
                        },
                        {
                            "id": "5e47d1c2e76841c34af7d617",
                            "imageUrl": "https://dailyissue.s3.ap-northeast-2.amazonaws.com/Banner_0215(2).png",
                            "state": "C",
                            "actionType": 0,
                            "data": null,
                            "createdAt": "2020-01-30T08:11:06.934+0000",
                            "updatedAt": "2020-01-30T08:11:06.934+0000"
                        }
                    ]
                """, Array<Banner>::class.java)
            return sampleJson.toList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBannerDetail(bannerId: String): BannerDetail {
        try {
            val sampleJson = Gson().fromJson<BannerDetail>("""
                    [
                        {
                            "id": "42125c77b-f3f6-466a-bd1a-0124555e5592",
                            "songId": null,
                            "key": "cuAg_tK2-C8",
                            "like": 12,
                            "view": 0,
                            "createdAt": "2020-01-30T08:11:06.934+0000",
                            "updatedAt": "2020-01-30T08:11:06.934+0000"
                        },
                        {
                            "id": "1238a433a-e120-459f-8bf1-1f55081dbf82",
                            "songId": null,
                            "key": "GRH2CMPIRoY",
                            "like": 123,
                            "view": 0,
                            "createdAt": "2020-01-30T11:54:32.761+0000",
                            "updatedAt": "2020-01-30T11:54:32.761+0000"
                        }
                    ]
                """, BannerDetail::class.java)
            return sampleJson
        } catch (e: Exception) {
            throw e
        }
    }
}