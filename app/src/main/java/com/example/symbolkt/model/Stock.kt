package com.example.symbolkt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Stock(
    val count: Int,
    @JsonProperty("result")
    val results: List<StockResult>
    )
