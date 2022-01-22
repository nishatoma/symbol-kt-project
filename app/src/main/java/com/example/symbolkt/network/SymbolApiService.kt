package com.example.symbolkt.network

import com.example.symbolkt.model.Stock
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SymbolApiService {

    @GET("search")
    suspend fun getStocks(@Query("q") name: String): Response<Stock>

    suspend fun getDetails()
}