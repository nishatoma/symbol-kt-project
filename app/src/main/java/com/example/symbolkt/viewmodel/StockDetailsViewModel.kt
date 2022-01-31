package com.example.symbolkt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.symbolkt.model.StockDetails
import com.example.symbolkt.model.StockResult
import com.example.symbolkt.network.SymbolApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StockDetailsViewModel(stockResult: StockResult) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    // Get profile2 details based on ticker symbol
    private val _stockDetails = MutableLiveData<StockDetails>()
    val stockDetails: LiveData<StockDetails>
        get() = _stockDetails

    // Get stock result from previous fragment
    // passed in from bundle
    private val _currStock = MutableLiveData<StockResult>()
    val currStock: LiveData<StockResult>
        get() = _currStock

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isDetailCardShown = MutableLiveData<Boolean>()
    val isDetailCardShown: LiveData<Boolean>
        get() = _isDetailCardShown

    init {
        _isDetailCardShown.value = false
        _currStock.value = stockResult
        _isLoading.value = false
        getStockDetails()
    }

    private fun getStockDetails() {

        coroutineScope.launch {
            _isLoading.postValue(true)
            val stockDetailsResponse = SymbolApi.retrofitService.getStockDetails(_currStock.value?.symbol!!)
            if (stockDetailsResponse.isSuccessful) {
                _stockDetails.postValue(stockDetailsResponse.body())
            } else {
                Log.e("StockDetailsViewModel", stockDetailsResponse.errorBody().toString())
            }
            _isLoading.postValue(false)
        }
    }

    fun toggleDetailsCard() {
        _isDetailCardShown.value = !(_isDetailCardShown.value)!!
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}