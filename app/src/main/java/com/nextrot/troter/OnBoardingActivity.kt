package com.nextrot.troter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.service.autofill.Validators.or
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.nextrot.troter.databinding.OnboardingActivityBinding
import org.koin.android.ext.android.inject
const val IS_FIRST = "is_first"
// TODO: indicator to RecyclerView.ItemDecoration
class OnBoardingActivity : AppCompatActivity(){
    private lateinit var onBoardingActivityBinding: OnboardingActivityBinding
    private val sharedPreferences:SharedPreferences by inject()
    private val boardings: List<OnBoarding> = listOf(
        OnBoarding(
            "트로트 듣기",
            "듣고 싶은 노래를 선택하면 한 번에\n재생할 수 있어유 <b>쉽쥬?</b>",
            "onboarding_play.json",
            "다음",
            R.drawable.ic_paging_1),
        OnBoarding(
            "가사 보기",
            "듣고 있는 노래의 가사를 보면서\n함께 따라 불러보셔유 <b>기가막히쥬?</b>",
            "onboarding_lylics.json",
            "다음",
            R.drawable.ic_paging_2),
        OnBoarding(
            "확대 하기",
            "가사가 잘 안보이면 오른쪽 위\n버튼을 눌러 확대해 보셔유! <b>지화자~!</b>",
            "onboarding_repeat.json",
            "트로트 들으러 가기",
            R.drawable.ic_paging_3)
    )
    private val it = boardings.iterator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardingActivityBinding = DataBindingUtil.setContentView(this, R.layout.onboarding_activity)
        onBoardingActivityBinding.activity = this
        onBoardingActivityBinding.lifecycleOwner = this
        onClickNext()
    }

    @SuppressLint("CommitPrefEdits")
    fun onClickNext(){
        if(!it.hasNext()){
            skipOnboarding()
        } else {
            val item = it.next()
            onBoardingActivityBinding.title.text=item.title
            onBoardingActivityBinding.subtitle.text= HtmlCompat.fromHtml(item.subtitle, HtmlCompat.FROM_HTML_MODE_LEGACY)
            onBoardingActivityBinding.lottieAnimationView.setAnimation(item.lottie)
            onBoardingActivityBinding.lottieAnimationView.repeatCount = 0
            onBoardingActivityBinding.lottieAnimationView.playAnimation()
            onBoardingActivityBinding.nextButton.text=item.next
            onBoardingActivityBinding.indicator.setImageResource(item.indicator)
        }
    }

    fun skipOnboarding(){
        sharedPreferences.edit().putBoolean(IS_FIRST, false).apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

data class OnBoarding(
    val title: String = "",
    val subtitle: String = "",
    val lottie: String = "",
    val next: String = "",
    val indicator:Int = 0
)