package com.nextrot.troter.base

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nextrot.troter.R
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.SongsFragmentBinding
import com.nextrot.troter.player.PlayerActivity
import com.nextrot.troter.songs.SongsViewModel
import com.nextrot.troter.songs.list.SongsListAdapter
import org.koin.android.ext.android.inject

class SongsFragment(private val songsViewModel: SongsViewModel) : Fragment() {
    private lateinit var binding: SongsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SongsFragmentBinding.inflate(layoutInflater, container, false).apply {
            fragment = this@SongsFragment
            viewmodel = songsViewModel
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        songsViewModel.selectedItems.observe(this.viewLifecycleOwner, Observer {
            // 옵저빙 해서 notify 함으로써 얻는 성능 저하는 감수해야함... 편할려고 ㅠ
            binding.list.adapter?.notifyDataSetChanged()
            changeCheckboxView()
        })

        songsViewModel.currentList.observe(this.viewLifecycleOwner, Observer {
            (binding.list.adapter as SongsListAdapter).submitList(it)
            binding.list.adapter?.notifyDataSetChanged()
        })

        binding.list.adapter = SongsListAdapter(songsViewModel, this)
    }

    fun onClickItem(item: Song) {
        songsViewModel.toggleSelectedItem(item)
    }

    fun onClickPlay(item: Song) {
        val intent = Intent(context, PlayerActivity::class.java).apply {
            putParcelableArrayListExtra(PlayerActivity.BUNDLE_SONGS, arrayListOf(item))
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        songsViewModel.clearSelectedItem()
    }

    fun onClickGoToPopular() {
        (context as AppCompatActivity).run {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    fun onClickSelectAll() {
        if (!songsViewModel.isAllSelected()) {
            songsViewModel.selectAll()
        } else {
            songsViewModel.clearSelectedItem()
        }
    }

    // 현재는 유튜브 링크 및 스토어 링를 공유하도록 한다.
    fun onClickShare(item: Song) {
        val content = "${item.singerName} - ${item.name}" +
            "\nhttp://youtou.be/${item.video[0].key}" +
            "\nhttp://play.google.com/store/apps/details?id=${context!!.applicationContext.packageName}"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "트로트 공유하기")
        startActivity(shareIntent)
    }

    private fun changeCheckboxView() {
        if (songsViewModel.isAllSelected()) {
            binding.selectAllCheckbox.setImageResource(R.drawable.checkbox_checked)
        } else {
            binding.selectAllCheckbox.setImageResource(R.drawable.checkbox_unchecked)
        }
    }
}