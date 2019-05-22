package com.example.minimoneybox.viewmodel

import com.example.minimoneybox.datasource.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimoneybox.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _bearerToken = MutableLiveData<String>()
    private val _error = MutableLiveData<String>()

    // custom accessors to expose private properties via LiveData
    val bearerToken : LiveData<String>
        get() = _bearerToken

    val error : LiveData<String>
        get() = _error


    fun makeLoginCall(email: String, password: String) {
        // use extension property lifecycle aware CoroutineScope
        viewModelScope.launch {
            val result = repository.login(email, password)
            when (result) {
                is Result.Success -> _bearerToken.postValue(result.data)
                is Result.Error -> _error.postValue(result.exception.message)
            }.exhaustive
        }
    }
}

/*
* Extension property to use on 'when' construct which treats it as an expression
* and enforces a compile-time check. Otherwise you could just comment out the error case and not handle it.
* This tells the compiler to force us to handle all 'when' branches.
* */
val <T> T.exhaustive: T
    get() = this