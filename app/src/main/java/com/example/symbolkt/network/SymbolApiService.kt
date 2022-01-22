package com.example.symbolkt.network

import com.example.symbolkt.model.Stock
import kotlinx.coroutines.Deferred
import retrofit2.Response
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
    suspend fun getStocks(@Query("q") name: String,
                          @Query("token") apiKey: String): Response<Stock>

}

object SymbolApi {
    val retrofitService: SymbolApiService by lazy {
        retrofit.create(SymbolApiService::class.java)
    }
}