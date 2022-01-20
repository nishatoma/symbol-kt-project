package com.example.symbolkt.network

import com.example.symbolkt.model.Stock
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://finnhub.io/api/v1/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface SymbolApiService {

    @GET("search")
    fun getStock(@Query("q") name: String,
                 @Query("token") apiKey: String): Deferred<Stock>

}

object SymbolApi {
    val retrofitService: SymbolApiService by lazy {
        retrofit.create(SymbolApiService::class.java)
    }
}