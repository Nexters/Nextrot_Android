package com.nextrot.troter.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nextrot.troter.IS_FIRST_PLAY_SONGS
import com.nextrot.troter.R
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.SongsFragmentBinding
import com.nextrot.troter.player.PlayerActivity
import com.nextrot.troter.songs.SongsViewModel
import com.nextrot.troter.songs.list.SongsListAdapter
import org.koin.android.ext.android.inject

class SongsFragment(private val songsViewModel: SongsViewModel) : Fragment() {
    private lateinit var binding: SongsFragmentBinding
    private val sharedPreferences: SharedPreferences by inject()

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
        })

        binding.list.adapter = SongsListAdapter(songsViewModel, this)
    }

    fun onClickItem(item: Song) {
        songsViewModel.toggleSelectedItem(item)
    }

    fun onClickPlay(item: Song) {
        if(sharedPreferences.getBoolean(IS_FIRST_PLAY_SONGS, true))
            showPlayerNoticeDialog()
        else
            startPlayerActivity(arrayListOf(item))
    }

    // TODO: SongsFragment와 MainActivity에서 중복 코드.. 어디로 빼지ㅎㅎ..
    fun showPlayerNoticeDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("백그라운드 재생 불가 안내")
        builder.setMessage("이 앱은 유튜브 영상재생 정책을 따릅니다.\n화면이 꺼지거나 재생영상이 화면에서 사라지면 재생이 불가하오니 이점 양해 부탁드립니다.")
        // TODO: message 또는 MultiChoice 양자택일 해야 합니다.. 리팩 때 디자인 나오면 수정 고고
        //  builder.setMultiChoiceItems(
        //      arrayOf("다시 보지 않기"), booleanArrayOf(false)) { dialog, which, isChecked -> }
        builder.setPositiveButton("확인") { dialog, which ->
            startPlayerActivity(songsViewModel.selectedItems.value!!)
            this.sharedPreferences.edit().putBoolean(IS_FIRST_PLAY_SONGS, false).apply()
        }
        val dialog = builder.create()
        dialog.show()

        // show로 dialog가 생성되어야함.. NPE 주의
        val positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveBtn.setTextColor(Color.parseColor("#1884ff"))
    }

    // TODO: SongsFragment와 MainActivity에서 중복 코드.. 어디로 빼지ㅎㅎ..
    fun startPlayerActivity(items: ArrayList<Song>){
        val intent = Intent(context, PlayerActivity::class.java).apply {
            putParcelableArrayListExtra(PlayerActivity.BUNDLE_SONGS, items)
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