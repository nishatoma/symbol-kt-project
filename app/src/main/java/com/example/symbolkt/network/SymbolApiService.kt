package com.example.symbolkt.network

import com.example.symbolkt.model.Stock
import com.example.symbolkt.model.StockDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SymbolApiService {

    @GET("search")
    suspend fun getStocks(@Query("q") name: String): Response<Stock>

    @GET("stock/profile2")
    suspend fun getStockDetails(@Query("symbol") symbol: String): Response<StockDetails>
}