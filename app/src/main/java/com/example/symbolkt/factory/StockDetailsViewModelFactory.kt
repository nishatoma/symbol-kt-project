package com.example.symbolkt.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.symbolkt.model.StockResult
import com.example.symbolkt.viewmodel.StockDetailsViewModel
import java.lang.IllegalArgumentException

class StockDetailsViewModelFactory(private val stockResult: StockResult): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockDetailsViewModel::class.java)) {
            return StockDetailsViewModel(stockResult) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}