package com.example.minimoneybox.viewmodel.userAccounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minimoneybox.repository.Repository

class UserAccountsViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserAccountsViewModel(repository) as T
    }
}