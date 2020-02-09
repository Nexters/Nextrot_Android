package com.nextrot.troter.singer

import android.os.Bundle
import android.view.View
import com.nextrot.troter.R
import com.nextrot.troter.base.BaseFragment
import com.nextrot.troter.databinding.SingerFragmentBinding
import com.nextrot.troter.search.list.SearchListAdapter
import com.nextrot.troter.singer.list.SingerListAdapter
import org.koin.android.viewmodel.ext.android.viewModel

internal class SingerFragment (private val index: Int) :
    BaseFragment<SingerFragmentBinding, SingerViewModel>() {
    override val layoutResId: Int
        get() = R.layout.singer_fragment
    override val baseViewModel: SingerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = baseViewModel
        baseViewModel.getAll("")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListView()
    }

    private fun setupListView() {
        binding.list.adapter = SingerListAdapter(baseViewModel, this)
    }

}