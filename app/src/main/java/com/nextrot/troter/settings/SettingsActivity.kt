package com.nextrot.troter.settings

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nextrot.troter.R

// TODO : 사용 하는 라이브러리들 가져오는 것 자동화
val openSourcesList = arrayListOf(
    "org.jetbrains.kotlin:kotlin-stdlib-jdk7",
    "androidx.appcompat:appcompat",
    "androidx.core:core-ktx",
    "androidx.constraintlayout:constraintlayout",
    "androidx.lifecycle:lifecycle-extensions",
    "androidx.legacy:legacy-support-v",
    "androidx.recyclerview:recyclerview",
    "com.android.support:multidex",
    "androidx.lifecycle:lifecycle-viewmodel-ktx",
    "com.google.android.gms:play-services-ads",
    "org.koin:koin-core",
    "org.koin:koin-core-ext",
    "org.koin:koin-android",
    "org.koin:koin-android-scope",
    "org.koin:koin-android-viewmodel",
    "com.squareup.retrofit2:retrofit",
    "com.squareup.retrofit2:converter-gson",
    "com.squareup.okhttp3:okhttp",
    "com.facebook.stetho:stetho-okhttp3",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core",
    "com.github.bumptech.glide:glide",
    "com.github.bumptech.glide:compiler",
    "com.google.android.gms:play-services-ad",
    "com.google.android.material:material",
    "com.pierfrancescosoffritti.androidyoutubeplayer:core",
    "com.airbnb.android:lottie"
)

class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        val openSourcesText = findViewById<TextView>(R.id.open_sources_text)

        openSourcesText.run {
            movementMethod = ScrollingMovementMethod()
            text = openSourcesList
                .map {"* $it"}
                .reduce { prev, cur -> prev + "\n" + cur}
        }
    }
}