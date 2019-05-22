package com.example.minimoneybox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.datasource.Result
import com.example.minimoneybox.datasource.model.investorProducts.AllInvestorProductData
import com.example.minimoneybox.repository.Repository
import kotlinx.coroutines.launch

class UserAccountsViewModel(private val repository: Repository) : ViewModel() {

    private val _userAccountData = MutableLiveData<AllInvestorProductData>()
    private val _error = MutableLiveData<String>()

    // custom accessors to expose private properties via LiveData
    val userAccountData : LiveData<AllInvestorProductData>
        get() = _userAccountData

    val error : LiveData<String>
        get() = _error

    fun makeUserAccountsCall(bearerToken: String?) {
        // use extension property lifecycle aware CoroutineScope
        viewModelScope.launch {
            val result = repository.getInvestorProducts(bearerToken)
            when (result) {
                is Result.Success -> _userAccountData.postValue(result.data)
                is Result.Error -> _error.postValue(result.exception.message)
            }.exhaustive
        }
    }
}