package com.nextrot.troter.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.SearchFragmentBinding
import com.nextrot.troter.search.list.SearchListAdapter
import org.koin.android.ext.android.inject

class SearchFragment(private val index: Int) : Fragment() {
    private val searchViewModel: SearchViewModel by inject()
    private lateinit var searchFragmentBinding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchFragmentBinding = SearchFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = searchViewModel
        }

        // TODO : 지금은 TAB_TITLE 을 검색 query 로 사용하도록 해놨음. 이 정책이 어디서 관리될 것인지 검토 필요
        val query = context!!.getString(SectionsPagerAdapter.TAB_TITLES[index])
        searchViewModel.search(query)

        return searchFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchFragmentBinding.lifecycleOwner = this.viewLifecycleOwner
        searchViewModel.selectedItems.observe(this.viewLifecycleOwner, Observer {
            // 옵저빙 해서 notify 함으로써 얻는 성능 저하는 감수해야함... 편할려고 ㅠ
            searchFragmentBinding.list.adapter?.notifyDataSetChanged()
        })
        setupListView()
    }

    private fun setupListView() {
        val viewmodel = searchFragmentBinding.viewmodel
        if (viewmodel != null) {
            searchFragmentBinding.list.adapter = SearchListAdapter(viewmodel, this)
        }
    }

    fun onClickItem(item: Item) {
        searchViewModel.toggleSelectedItem(item)
    }
}