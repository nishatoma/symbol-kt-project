package com.example.symbolkt.model

import com.google.gson.annotations.SerializedName


data class Stock(
    val count: Int,
    @SerializedName("result")
    val results: List<StockResult>
)
