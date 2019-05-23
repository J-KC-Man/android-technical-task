package com.example.minimoneybox.viewmodel.quickPayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minimoneybox.repository.Repository

class QuickPaymentViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuickPaymentViewModel(repository) as T
    }
}