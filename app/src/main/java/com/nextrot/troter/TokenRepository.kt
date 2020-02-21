package com.nextrot.troter

import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.util.*

class TokenRepository {
    var sharedPreferences: SharedPreferences? = null
    private val httpClient = OkHttpClient
        .Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    var token: String? = null
    var uuid: String = ""

    fun getProperToken(): String {
        if (!token.isNullOrEmpty()) {
            return token!!
        }
        val newToken = requestNewToken()
        token = newToken
        saveRemoteToken()
        return token!!
    }

    fun loadUuid() {
        var uuid = sharedPreferences?.getString(PREF_UUID, "") ?: ""
        if (uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString()
        }
        this.uuid = uuid
        saveUuid()
    }

    fun loadToken() {
        val token = sharedPreferences?.getString(PREF_REMOTE_TOKEN, "") ?: ""
        this.token = token
    }

    private fun requestNewToken(): String {
        val response: String =
            httpClient
                .newCall(makeTokenRequest(uuid))
                .execute()
                .body()!!
                .string()

        return JSONObject(response).getString("accessToken")
    }

    private fun makeTokenRequest(uid: String): Request {
        return Request
            .Builder()
            .url("${BuildConfig.API_ADDRESS}/api/v1/auth/token")
            .post(RequestBody.create(MediaType.parse("application/json"),  """{ "UID": "$uid" } }"""))
            .build()
    }

    fun requestRefreshToken(): String {
        val response: String =
            httpClient
                .newCall(makeRefreshTokenRequest(token!!))
                .execute()
                .body()!!
                .string()

        val result = JSONObject(response).getString("refreshToken")
        token = result
        return result
    }

    private fun makeRefreshTokenRequest(oldToken: String): Request {
        return Request
            .Builder()
            .url("${BuildConfig.API_ADDRESS}/api/v1/auth/token/refresh")
            .post(RequestBody.create(MediaType.parse("application/json"),  """{ "refreshToken": "$oldToken" } }"""))
            .build()
    }

    private fun saveUuid() {
        sharedPreferences?.edit()?.putString(PREF_UUID, uuid)?.apply()
    }

    private fun saveRemoteToken() {
        sharedPreferences?.edit()?.putString(PREF_REMOTE_TOKEN, token)?.apply()
    }
}