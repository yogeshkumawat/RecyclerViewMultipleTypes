package com.recyclerview.multipletypes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.recyclerview.multipletypes.MainRepository
import com.recyclerview.multipletypes.network.APIHelper
import com.recyclerview.multipletypes.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: APIHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}