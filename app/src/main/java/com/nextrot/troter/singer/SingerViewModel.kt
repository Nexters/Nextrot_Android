package com.nextrot.troter.singer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextrot.troter.data.Item
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.launch
import java.util.*

internal class SingerViewModel(private val repo: VideoRepository): ViewModel(){
    val singers = MutableLiveData<List<Item>>(Collections.emptyList())

    fun getAll(query: String){
        // TODO: Repository에서 호출하기
        viewModelScope.launch {
            try {
                val result = repo.search(query)
                singers.value = result?.items ?: Collections.emptyList()
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }
}