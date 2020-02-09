package com.nextrot.troter.singers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextrot.troter.TroterViewModel
import com.nextrot.troter.databinding.SingersFragmentBinding
import org.koin.android.ext.android.inject

class SingersFragment : Fragment() {
    private val troterViewModel: TroterViewModel by inject()
    private lateinit var singersFragmentBinding: SingersFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        singersFragmentBinding = SingersFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = troterViewModel
        }
        troterViewModel.getSingers()
        return singersFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        singersFragmentBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListView()
    }

    private fun setupListView() {
        val viewmodel = singersFragmentBinding.viewmodel
        if (viewmodel != null) {
            singersFragmentBinding.list.adapter = SingersListAdapter(viewmodel, this)
        }
    }

    fun onClickItem(singer: String) {
    }
}