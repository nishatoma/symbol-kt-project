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

    // Change fragment title on start
    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle: LiveData<String>
        get() = _fragmentTitle

    // Variable for storing stock result
    private val _stockResults = MutableLiveData<List<StockResult>>()
    val stockResults: LiveData<List<StockResult>>
        get() = _stockResults

    // Variable to tell if Retrofit is loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    // Variable to tell if Retrofit is loading
    private val _isCallSuccessful = MutableLiveData<Boolean>()
    val isCallSuccessful: LiveData<Boolean>
        get() = _isCallSuccessful

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    // I copied this Job() from online, don't know how it works yet
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {
        _searchQuery.value = ""
        _isLoading.value = false
        _isCallSuccessful.value = false
        _fragmentTitle.value = FRAGMENT_TITLE
    }

    private fun getStockResults(query: String) {
        coroutineScope.launch {
            _isLoading.postValue(true)
            val stockResponse = SymbolApi.retrofitService.getStocks(query)

            withContext(Dispatchers.Main) {
                if (stockResponse.isSuccessful) {
                    _stockResults.postValue(stockResponse.body()?.results)
                    _isCallSuccessful.postValue(stockResponse.body()?.count!! > 0)
                } else {
                    _isCallSuccessful.postValue(false)
                }
                _isLoading.value = false
            }

        }
    }

    fun updateQuery(q: String) {
        _searchQuery.value = q
    }

    fun onSearchClicked() {
        getStockResults(_searchQuery.value!!)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}