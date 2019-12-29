package com.nextrot.troter.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextrot.troter.databinding.PageFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PageFragment private constructor(): Fragment() {
    private val pageViewModel: PageViewModel by viewModel()
    private lateinit var pageFragmentBinding: PageFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pageFragmentBinding = PageFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = pageViewModel
        }
        return pageFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pageFragmentBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PageFragment {
            return PageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}