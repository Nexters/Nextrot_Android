package com.nextrot.troter.songs

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextrot.troter.data.Banner
import com.nextrot.troter.data.Singer
import com.nextrot.troter.data.Song
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.launch


class SongsViewModel(private val repo: VideoRepository): ViewModel() {
    val selectedItems = MutableLiveData<ArrayList<Song>>(ArrayList())
    val singers = MutableLiveData<ArrayList<Singer>>(ArrayList())
    val currentList = MutableLiveData<ArrayList<Song>>(ArrayList())
    val banners = MutableLiveData<ArrayList<Banner>>(ArrayList())

    fun getBanners() {
        viewModelScope.launch {
            try {
                val result = repo.getBanners()
                banners.value = ArrayList(result)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    fun getPopular() {
        viewModelScope.launch {
            try {
                val result = repo.popular()
                currentList.value = ArrayList(result)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    fun getSingers() {
        viewModelScope.launch {
            try {
                val result = repo.singers()
                singers.value = ArrayList(result)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    fun getSongsOfSinger(singerId: String) {
        viewModelScope.launch {
            try {
                val result = repo.songsOfSinger(singerId)
                currentList.value = ArrayList(result)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    // Fake repository
    fun getSongsOfSingerByName(singerName: String) {
        viewModelScope.launch {
            try {
                val result = repo.songsOfSingerByName(singerName)
                currentList.value = ArrayList(result)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    fun clearSelectedItem() {
        if (selectedItems.value.isNullOrEmpty()) {
            return
        }

        selectedItems.value = arrayListOf()
    }

    fun selectAll() {
        if (isAllSelected()) {
            return
        }

        clearSelectedItem()
        selectedItems.value = ArrayList(currentList.value ?: arrayListOf())
    }

    fun isAllSelected(): Boolean {
        if (currentList.value.isNullOrEmpty()) {
            return false
        }

        return selectedItems.value?.size == currentList.value?.size
    }

    private fun addSelectedItem(item: Song) {
        val nextItems = selectedItems.value!!.apply {
            add(item)
        }
        selectedItems.value = ArrayList(nextItems)
    }

    private fun removeSelectedItem(item: Song) {
        val nextItems = selectedItems.value!!.apply {
            remove(item)
        }
        selectedItems.value = ArrayList(nextItems)
    }

    fun toggleSelectedItem(item: Song) {
        if (selectedItems.value!!.contains(item)) {
            removeSelectedItem(item)
        } else {
            addSelectedItem(item)
        }
    }

    fun isSelected(item: Song): Boolean = selectedItems.value!!.contains(item)

    fun getSavedPlaylist() {
        viewModelScope.launch {
            try {
                val playlist = repo.getSavedPlaylist()
                currentList.value = ArrayList(playlist)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    fun getBannerDetail(banner: Banner) {
        viewModelScope.launch {
            try {
                val playlist = repo.getBannerDetail(banner.key, banner.actionType)
                currentList.value = ArrayList(playlist)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }
}