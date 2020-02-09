package com.nextrot.troter.search

import android.os.Bundle
import android.view.View
import com.nextrot.troter.R
import com.nextrot.troter.base.BaseFragment
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.SearchFragmentBinding
import com.nextrot.troter.search.list.SearchListAdapter
import org.koin.android.viewmodel.ext.android.viewModel

internal class SearchFragment(private val index: Int) : BaseFragment<SearchFragmentBinding, SearchViewModel>() {
    override val layoutResId: Int
        get() = R.layout.search_fragment
    override val baseViewModel: SearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = baseViewModel
        binding.list.apply {
            onCreate(savedInstanceState)
            // TODO : 지금은 TAB_TITLE 을 검색 query 로 사용하도록 해놨음. 이 정책이 어디서 관리될 것인지 검토 필요
            val query = context!!.getString(SectionsPagerAdapter.TAB_TITLES[index])
            baseViewModel.search(query)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListView()

    }

    private fun setupListView() {
        binding.list.adapter = SearchListAdapter(baseViewModel, this)
    }

    fun onClickItem(item: Item) {
        val viewmodel = binding.viewmodel
        viewmodel!!.toggleSelectedItem(item)
        binding.list.adapter?.notifyDataSetChanged()
    }


}