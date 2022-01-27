package com.example.symbolkt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StockResult(
    val description: String?,
    val displaySymbol: String?,
    val symbol: String?,
    val type: String?
) : Parcelable
