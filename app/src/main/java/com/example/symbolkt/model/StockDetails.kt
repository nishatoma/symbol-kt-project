package com.example.symbolkt.model

import com.google.gson.annotations.SerializedName

data class StockDetails(
    val country: String?,
    val currency: String?,
    val exchange: String?,
    @SerializedName("finnhubIndustry")
    val industry: String?,
    @SerializedName("ipo")
    val ipoDate: String?,
    val logo: String?,
    @SerializedName("marketCapitalization")
    val mcap: Int?,
    val name: String?,
    val phone: Double?,
    @SerializedName("shareOutstanding")
    val shares: Double?,
    val ticker: String?,
    @SerializedName("weburl")
    val website: String?
)
