package com.example.symbolkt.network

import com.example.symbolkt.model.Stock
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://finnhub.io/api/v1/"
private const val API_KEY = "c7ke93iad3i9q0uqoa70"

// so we don't have to pass api key for every single
// request as a query parameter..
val apiInterceptor = Interceptor {
    val originalRequest = it.request()
    val newHttpUrl = originalRequest.url().newBuilder()
        .addQueryParameter("token", API_KEY)
        .build()
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

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttp)
    .baseUrl(BASE_URL)
    .build()

interface SymbolApiService {

    @GET("search")
    suspend fun getStocks(@Query("q") name: String): Response<Stock>

}

object SymbolApi {
    val retrofitService: SymbolApiService by lazy {
        retrofit.create(SymbolApiService::class.java)
    }
}