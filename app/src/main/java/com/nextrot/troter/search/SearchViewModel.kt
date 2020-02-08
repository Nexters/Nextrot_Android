package com.nextrot.troter.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nextrot.troter.data.Item
import com.nextrot.troter.data.SearchResult
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import java.util.*

class SearchViewModel(private val repo: VideoRepository): ViewModel() {
    val searchResult = MutableLiveData<List<Item>>(Collections.emptyList())


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
    fun toggleSelect(item: Item){
        item.selected = !item.selected
        println(item.selected)
    }
}