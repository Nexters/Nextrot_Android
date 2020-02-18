package com.nextrot.troter.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nextrot.troter.PREF_SAVED_SONGS
import com.nextrot.troter.data.remote.RemoteClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.IOException

interface VideoRepository {
    suspend fun popular(): List<Song>
    suspend fun singers(): List<Singer>
    suspend fun songsOfSinger(singerId: String): List<Song>
    suspend fun songsOfSingerByName(singerName: String): List<Song>
    suspend fun savePlaylist(songs: List<Song>)
    suspend fun getSavedPlaylist(): List<Song>
    suspend fun getVideosOfSong(songId: String): List<String>
    suspend fun getBanners(): List<Banner>
    suspend fun getBannerDetail(bannerId: String): List<Song>
}

class RemoteVideoRepository(private val remoteClient: RemoteClient) : VideoRepository {
    override suspend fun popular(): List<Song> {
        return remoteClient.getPopular("view", 30).filter {
            !it.video.isNullOrEmpty() && !it.video[0].key.isNullOrEmpty()
        }
    }

    override suspend fun singers(): List<Singer> {
        return remoteClient.getSingers(0, 20)
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        return remoteClient.getSongsOfSinger(singerId, 0, 30).filter {
            !it.video.isNullOrEmpty() && !it.video[0].key.isNullOrEmpty()
        }
    }

    override suspend fun songsOfSingerByName(singerName: String): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override suspend fun getBannerDetail(bannerId: String): List<Song> {
        return remoteClient.getBannerDetail(bannerId).filter {
            !it.video.isNullOrEmpty() && !it.video[0].key.isNullOrEmpty()
        }
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

    override suspend fun songsOfSingerByName(singerName: String): List<Song> {
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

    override suspend fun getBannerDetail(bannerId: String): List<Song> {
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

    override suspend fun songsOfSingerByName(singerName: String): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override suspend fun getBannerDetail(bannerId: String): List<Song> {
        return remote.getBannerDetail(bannerId)
    }
}

class FakeVideoRepository(private val sharedPreferences: SharedPreferences, private val application: Application): VideoRepository {
    @Throws(IOException::class)
    private fun Context.readJsonFromFile(path: String): String {
        val inputStream = assets.open(path)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }

    override suspend fun singers(): List<Singer> {
        try {
            val jsonString = GlobalScope.async(Dispatchers.IO)  {
                application.readJsonFromFile("singers.json")
            }.await()
            val singers = Gson().fromJson<Array<Singer>>(jsonString, Array<Singer>::class.java)
            return singers.toList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun popular(): List<Song> {
        try {
            val jsonString = GlobalScope.async(Dispatchers.IO)  {
                application.readJsonFromFile("popular.json")
            }.await()
            val singers = Gson().fromJson<Array<Song>>(jsonString, Array<Song>::class.java)
            return singers.toList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun songsOfSinger(singerId: String): List<Song> {
        try {
            val jsonString = GlobalScope.async(Dispatchers.IO)  {
                application.readJsonFromFile("${singerId}.json")
            }.await()
            val singers = Gson().fromJson<Array<Song>>(jsonString, Array<Song>::class.java)
            return singers.toList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun songsOfSingerByName(singerName: String): List<Song> {
        try {
            val jsonString = GlobalScope.async(Dispatchers.IO)  {
                application.readJsonFromFile("${singerName}.json")
            }.await()
            val singers = Gson().fromJson<Array<Song>>(jsonString, Array<Song>::class.java)
            return singers.toList()
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
            val jsonString = GlobalScope.async(Dispatchers.IO)  {
                application.readJsonFromFile("banners.json")
            }.await()
            val singers = Gson().fromJson<Array<Banner>>(jsonString, Array<Banner>::class.java)
            return singers.toList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBannerDetail(bannerId: String): List<Song> {
        try {
            val jsonString = GlobalScope.async(Dispatchers.IO)  {
                application.readJsonFromFile("banner_${bannerId}.json")
            }.await()
            val singers = Gson().fromJson<Array<Song>>(jsonString, Array<Song>::class.java)
            return singers.toList()
        } catch (e: Exception) {
            throw e
        }
    }
}