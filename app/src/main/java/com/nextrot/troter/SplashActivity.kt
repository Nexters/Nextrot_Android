package com.nextrot.troter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*


// TODO: 억지로 넣은 Splash 이고 대단히 좋지 않음, 릴리즈 떄 제거 고려
// Activity 로 넣을 게 아니라 app theme 으로 넣어야 한다.
class SplashActivity: AppCompatActivity() {
    private val repo: VideoRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        // 일부러 메인화면에 뿌릴 몇가지만 불러오고 캐싱하도록 해버렸다
        GlobalScope.launch(Dispatchers.IO) {
            repo.popular()
            repo.getBanners()
            repo.singers()
        }
        val timer = Timer()
        timer.schedule(SplashTimer(), 1500)
    }

    inner class SplashTimer: TimerTask() {
        override fun run() {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(intent)
            this@SplashActivity.finish()
        }
    }
}