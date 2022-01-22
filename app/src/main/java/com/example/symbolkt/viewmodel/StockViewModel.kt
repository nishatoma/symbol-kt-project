package com.example.symbolkt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.symbolkt.model.StockResult
import com.example.symbolkt.network.SymbolApi
import kotlinx.coroutines.*


private const val FRAGMENT_TITLE = "Stocks"

class StockViewModel: ViewModel() {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle: LiveData<String>
        get() = _fragmentTitle

    private val _stockResults = MutableLiveData<List<StockResult>>()
    val stockResults: LiveData<List<StockResult>>
        get() = _stockResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {
        _isLoading.value = false
        _fragmentTitle.value = FRAGMENT_TITLE
        getStockResults()
    }

    private fun getStockResults() {
        coroutineScope.launch {
            _isLoading.postValue(true)
            val stockResponse = SymbolApi.retrofitService.getStocks("AAPL")

            withContext(Dispatchers.Main) {
                if (stockResponse.isSuccessful) {
                    _stockResults.postValue(stockResponse.body()?.results)
                    Log.d("StockViewModel", _stockResults.value.toString())
                } else {
                    Log.d("StockViewModel", stockResponse.errorBody().toString())
                }
                _isLoading.value = false
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}