package com.nextrot.troter.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextrot.troter.databinding.SearchFragmentBinding
import com.nextrot.troter.search.list.SearchListAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var searchFragmentBinding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchFragmentBinding = SearchFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = searchViewModel
        }
        return searchFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchFragmentBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListView()
    }

    private fun setupListView() {
        val viewmodel = searchFragmentBinding.viewmodel
        if (viewmodel != null) {
            searchFragmentBinding.list.adapter = SearchListAdapter(viewmodel)
        }
    }
}