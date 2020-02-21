package com.nextrot.troter

import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nextrot.troter.data.FakeVideoRepository
import com.nextrot.troter.data.LocalVideoRepository
import com.nextrot.troter.data.RemoteVideoRepository
import com.nextrot.troter.data.TroterVideoRepository
import com.nextrot.troter.data.remote.RESPONSE_SUCCESS
import com.nextrot.troter.data.remote.RemoteClient
import com.nextrot.troter.data.remote.TOKEN_EXPIRED
import com.nextrot.troter.player.PlayerActivity
import com.nextrot.troter.singers.SingersFragment
import com.nextrot.troter.songs.PlayerViewModel
import com.nextrot.troter.songs.SongsActivity
import com.nextrot.troter.songs.SongsViewModel
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import org.koin.android.ext.android.inject
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
const val PREF_REMOTE_TOKEN = "PREF_REMOTE_TOKEN"
const val PREF_UUID = "PREF_UUID"
val tokenRepository = TokenRepository()

class AuthHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.header("Authorization").isNullOrEmpty()) {
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer " + tokenRepository.getProperToken())
                .build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(request)
    }
}

class RefreshAuthenticator: Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val originalRequest = response.request()
        val resultCode = JSONObject(response.body()!!.string()).getInt("statusCode")
        return if (resultCode == TOKEN_EXPIRED) {
            val refreshToken = tokenRepository.requestRefreshToken()
            originalRequest
                .newBuilder()
                .addHeader("Authorization", "Bearer $refreshToken")
                .build()

        } else {
            originalRequest
        }
    }
}

class ResponseBodyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val responseBody = response.body()!!.string()
        return when (JSONObject(responseBody).get("statusCode")) {
            RESPONSE_SUCCESS -> {
                val data = JSONObject(responseBody).get("data")
                if (data != null) {
                    val extractedBody = ResponseBody
                        .create(response.body()!!.contentType(), data.toString())
                    response.newBuilder().body(extractedBody).build()
                } else {
                    response.newBuilder().body(response.body()).build()
                }
            }
            else -> response
        }
    }
}

val appModule = module {
    single<Retrofit> {
        val httpClient = OkHttpClient
            .Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .addNetworkInterceptor(StethoInterceptor())
            .addNetworkInterceptor(AuthHeaderInterceptor())
            .authenticator(RefreshAuthenticator())
            .addInterceptor(ResponseBodyInterceptor())
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
            FakeVideoRepository(get(), androidApplication())
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
    single { tokenRepository }
}

@Suppress("unused")
class TroterApplication : MultiDexApplication() {
    private val sharedPreferences: SharedPreferences by inject()
    private val tokenRepository: TokenRepository by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TroterApplication)
            modules(appModule)
        }
        tokenRepository.run {
            sharedPreferences = this@TroterApplication.sharedPreferences
            loadUuid()
            loadToken()
        }
        Stetho.initializeWithDefaults(this)
    }
}
