package com.example.minimoneybox.viewmodel.quickPayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.datasource.Result
import com.example.minimoneybox.repository.Repository
import com.example.minimoneybox.viewmodel.login.exhaustive
import kotlinx.coroutines.launch

class QuickPaymentViewModel(private val repository: Repository) : ViewModel() {

    private val _quickPaymentResult = MutableLiveData<String>()
    private val _error = MutableLiveData<String>()

    // custom accessors to expose private properties via LiveData
    val quickPaymentResult : LiveData<String>
        get() = _quickPaymentResult

    val error : LiveData<String>
        get() = _error

    fun makePaymentCall(bearerToken: String?, amount: String, investorProductId: String?) {
        // use extension property lifecycle aware CoroutineScope
        viewModelScope.launch {
            val result = repository.addOneOffPayment(bearerToken, amount, investorProductId)
            when (result) {
                is Result.Success -> _quickPaymentResult.postValue(result.data?.Moneybox)
                is Result.Error -> _error.postValue(result.exception.message)
            }.exhaustive
        }
    }
}