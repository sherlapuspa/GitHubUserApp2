package com.dicoding.githubuserapp.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val secretKey = com.dicoding.githubuserapp.BuildConfig.KEY

    val authIntercept = Interceptor { chain ->
        val request = chain.request()
        val reqHead = request.newBuilder().addHeader("Authorization", "token" + secretKey).build()
        chain.proceed(reqHead)
    }
    private val client = OkHttpClient.Builder().addInterceptor(authIntercept).build()

    private const val BASE_URL = com.dicoding.githubuserapp.BuildConfig.BASE_URL

    val retro =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(client).build()

    val apiInstance = retro.create(ApiService::class.java)
}