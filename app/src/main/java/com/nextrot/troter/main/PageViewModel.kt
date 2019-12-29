package com.nextrot.troter.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


// 이게 진짜 VM 이다. Presenter 가 하던걸 여기서 시키면 될 것 같다.

class PageViewModel: ViewModel() {
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}