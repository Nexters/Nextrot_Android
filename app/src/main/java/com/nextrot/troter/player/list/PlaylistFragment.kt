package com.nextrot.troter.player.list

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.PlaylistFragmentBinding

class PlaylistFragment(private val list: ArrayList<Item>) : Fragment() {
    private lateinit var playlistFragmentBinding: PlaylistFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playlistFragmentBinding = PlaylistFragmentBinding.inflate(inflater, container, false).apply {
            list = ObservableArrayList<Item>().apply {
                addAll(this@PlaylistFragment.list)
            }
        }
        return playlistFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        playlistFragmentBinding.lifecycleOwner = viewLifecycleOwner
        setupListView()
    }

    private fun setupListView() {
        playlistFragmentBinding.listView.apply {
            adapter = PlaylistAdapter(this@PlaylistFragment)
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    itemPosition: Int,
                    parent: RecyclerView
                ) {
                    super.getItemOffsets(outRect, itemPosition, parent)
                    outRect.top += TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
                }
            })
        }
    }

    fun onClickItem(item: Item) {
        // TODO: 클릭 시 재생 처리
    }
}