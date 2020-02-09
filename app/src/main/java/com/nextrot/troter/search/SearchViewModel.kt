package com.nextrot.troter.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextrot.troter.data.Item
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.launch
import java.util.*

internal class SearchViewModel(private val repo: VideoRepository): ViewModel() {
    val searchResult = MutableLiveData<List<Item>>(Collections.emptyList())
    val selectedItems = MutableLiveData<ArrayList<Item>>(arrayListOf())

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val result = repo.search(query)
                searchResult.value = result?.items ?: Collections.emptyList()
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