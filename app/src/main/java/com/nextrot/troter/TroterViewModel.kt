package com.nextrot.troter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextrot.troter.data.Item
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.launch
import java.util.*


class TroterViewModel(private val repo: VideoRepository): ViewModel() {
    val popular = MutableLiveData<ArrayList<Item>>(arrayListOf())
    val selectedItems = MutableLiveData<ArrayList<Item>>(arrayListOf())
    val singers = MutableLiveData<ArrayList<String>>(arrayListOf())
    val songsOfSinger = MutableLiveData<ArrayList<Item>>(arrayListOf())

    fun getPopular() {
        viewModelScope.launch {
            try {
                val result = repo.popular()
                popular.value = result
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

    fun getSongsOfSinger(singer: String) {
        viewModelScope.launch {
            try {
                val result = repo.songsOfSinger(singer)
                songsOfSinger.value = result
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    fun clearSelectedItem() {
        selectedItems.value = arrayListOf()
    }

    private fun addSelectedItem(item: Item) {
        val nextItems = selectedItems.value!!.apply {
            add(item)
        }
        selectedItems.value = ArrayList(nextItems)
    }

    private fun removeSelectedItem(item: Item) {
        val nextItems = selectedItems.value!!.apply {
            remove(item)
        }
        selectedItems.value = ArrayList(nextItems)
    }

    fun toggleSelectedItem(item: Item) {
        if (selectedItems.value!!.contains(item)) {
            removeSelectedItem(item)
        } else {
            addSelectedItem(item)
        }
    }

    fun isSelected(item: Item): Boolean = selectedItems.value!!.contains(item)
}