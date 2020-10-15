package com.yanbin.githubbrowser.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val retrofit: Retrofit
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY; })
        .build()

    companion object {
        const val BASE_URL = "https://api.github.com/"

        @Volatile
        private var INSTANCE: ApiService? = null

        fun getInstance(): ApiService {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                INSTANCE = ApiService()
                return INSTANCE!!
            }
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val githubApi = retrofit.create(GithubApi::class.java)
}