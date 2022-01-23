package com.example.symbolkt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.symbolkt.model.StockDetails
import com.example.symbolkt.network.SymbolApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StockDetailsViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _stockDetails = MutableLiveData<StockDetails>()
    val stockDetails: LiveData<StockDetails>
        get() = _stockDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        _isLoading.value = false
        getStockDetails()
    }

    private fun getStockDetails() {

        coroutineScope.launch {
            _isLoading.postValue(true)
            val stockDetailsResponse = SymbolApi.retrofitService.getStockDetails("aapl")
            if (stockDetailsResponse.isSuccessful) {
                _stockDetails.postValue(stockDetailsResponse.body())
            } else {
                Log.e("StockDetailsViewModel", stockDetailsResponse.errorBody().toString())
            }
            _isLoading.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}