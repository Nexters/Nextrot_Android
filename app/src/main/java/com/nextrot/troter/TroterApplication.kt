package com.nextrot.troter

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.nextrot.troter.data.*
import com.nextrot.troter.data.remote.RemoteClient
import com.nextrot.troter.player.PlayerActivity
import com.nextrot.troter.singers.SingersFragment
import com.nextrot.troter.songs.PlayerViewModel
import com.nextrot.troter.songs.SongsActivity
import com.nextrot.troter.songs.SongsViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TROTER_PREF = "TROTER_PREF"
const val PREF_SAVED_SONGS = "PREF_SAVED_SONGS"

val appModule = module {
    single<Retrofit> {
        val httpClient = OkHttpClient
            .Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
            .addNetworkInterceptor(StethoInterceptor())
            .addNetworkInterceptor(object: Interceptor {
                // Auth token header
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + BuildConfig.AUTH_TOKEN)
                        .build()
                    return chain.proceed(request)
                }

            })
            .addInterceptor(object: Interceptor {
                // response body extractor
                override fun intercept(chain: Interceptor.Chain): Response {
                    val response = chain.proceed(chain.request())
                    val parsed = Gson().fromJson(response.body?.string(), TroterResponse::class.java)
                    return if (parsed.statusCode == RESPONSE_SUCCESS) {
                        val extractedBody = Gson()
                            .toJson(parsed.data)
                            .toString()
                            .toResponseBody(response.body?.contentType())
                        response.newBuilder().body(extractedBody).build()
                    } else {
                        // TODO : global error handle
                        response
                    }
                }

            })
            .build()

        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(BuildConfig.API_ADDRESS)
            .build()
    }
    single<RemoteClient> { (get(Retrofit::class.java) as Retrofit).create(RemoteClient::class.java) }
    single {
        if (BuildConfig.DEBUG) {
            FakeVideoRepository(get())
        } else {
            TroterVideoRepository(get(), get())
        }
    }
    single { LocalVideoRepository(get()) }
    single { RemoteVideoRepository(get()) }
    viewModel { SongsViewModel(get()) }
    viewModel { PlayerViewModel(get()) }
    factory { MainActivity() }
    factory { PlayerActivity() }
    factory { SongsActivity() }
    factory { SingersFragment() }
    single { androidApplication().getSharedPreferences(TROTER_PREF, Context.MODE_PRIVATE) }
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
