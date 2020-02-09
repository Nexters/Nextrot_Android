package com.nextrot.troter

import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nextrot.troter.data.FakeVideoRepository
import com.nextrot.troter.data.RemoteVideoRepository
import com.nextrot.troter.data.remote.RemoteClient
import com.nextrot.troter.search.SearchFragment
import com.nextrot.troter.singer.SingerFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<Retrofit> {
        val httpClient = OkHttpClient
            .Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(BuildConfig.API_ADDRESS)
            .build()
    }
    single<RemoteClient> { (get(Retrofit::class.java) as Retrofit).create(RemoteClient::class.java) }
//    single<VideoRepository> { RemoteVideoRepository(get()) }
    single {
        if (BuildConfig.DEBUG) {
            FakeVideoRepository(get())
        } else {
            RemoteVideoRepository(get())
        }
    }
    single { TroterViewModel(get()) }
    factory { MainActivity() }
    factory<ArrayList<Fragment>> {
        arrayListOf(
            SearchFragment(0),
            SingerFragment(1)
        )
    }
}


// TODO: Nexus 4 API 19 emulator 에서 앱 크래시 현상 확인 필요
@Suppress("unused")
class TroterApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TroterApplication)
            modules(appModule)
        }
        Stetho.initializeWithDefaults(this)
    }
}
