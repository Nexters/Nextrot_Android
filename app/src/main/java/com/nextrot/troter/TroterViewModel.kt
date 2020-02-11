package com.nextrot.troter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextrot.troter.data.Singer
import com.nextrot.troter.data.Song
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class TroterViewModel(private val repo: VideoRepository): ViewModel() {
    val selectedItems = MutableLiveData<ArrayList<Song>>(arrayListOf())
    val singers = MutableLiveData<ArrayList<Singer>>(arrayListOf())
    val songsOfSinger = MutableLiveData<ArrayList<Song>>(arrayListOf())


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
                songsOfSinger.value = ArrayList(result)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    fun clearSelectedItem() {
        selectedItems.value = arrayListOf()
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
}