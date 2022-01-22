package com.example.symbolkt.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://finnhub.io/api/v1/"
private const val API_KEY = "c7ke93iad3i9q0uqoa70"

// so we don't have to pass api key for every single
// request as a query parameter..
val apiInterceptor = Interceptor {
    val originalRequest = it.request()
    // Take the old URL and attach the API
    // key to it
    val newHttpUrl = originalRequest.url().newBuilder()
        .addQueryParameter("token", API_KEY)
        .build()
    // Modify the request to take in
    // the new URL
    val newRequest = originalRequest.newBuilder()
        .url(newHttpUrl)
        .build()
    // submit the modified request
    it.proceed(newRequest)
}

// okhttp client with the interceptor
private val okhttp = OkHttpClient().newBuilder()
    .addInterceptor(apiInterceptor)
    .build()

// retrofit instantiation
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttp)
    .baseUrl(BASE_URL)
    .build()

object SymbolApi {
    val retrofitService: SymbolApiService by lazy {
        retrofit.create(SymbolApiService::class.java)
    }
}

