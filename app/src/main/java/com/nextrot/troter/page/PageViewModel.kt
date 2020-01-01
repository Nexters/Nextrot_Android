package com.nextrot.troter.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nextrot.troter.data.TestRepository


// 이게 진짜 VM 이다. Presenter 가 하던걸 여기서 시키면 될 것 같다.

class PageViewModel(private val repo: TestRepository): ViewModel() {
    private val _index = MutableLiveData<Int>()

    val text: LiveData<String> = Transformations.map(_index) {
        "${repo.giveData()} $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}