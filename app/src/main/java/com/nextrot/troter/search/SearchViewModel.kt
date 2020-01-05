package com.nextrot.troter.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextrot.troter.data.Item
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.launch
import java.util.*

class SearchViewModel(private val repo: VideoRepository): ViewModel() {
    val searchResult = MutableLiveData<List<Item>>(Collections.emptyList())

    init {

        // 임시 데이터로 안녕하세요를 검색
        search("안녕하세요")
    }

    private fun search(query: String) {
        viewModelScope.launch {
            try {
                val result = repo.search(query)
                searchResult.value = result?.items ?: Collections.emptyList()
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }
}