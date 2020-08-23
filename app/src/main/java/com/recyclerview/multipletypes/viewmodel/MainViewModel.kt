package com.recyclerview.multipletypes.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.recyclerview.multipletypes.MainRepository
import com.recyclerview.multipletypes.model.ListData
import com.recyclerview.multipletypes.model.Result
import io.reactivex.Observable

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    var progressBarVisibility: ObservableBoolean = ObservableBoolean(true)

    var listVisibility: ObservableBoolean = ObservableBoolean(false)

    fun fetchListData(url: String): Observable<Result<ListData>> {
        return mainRepository.fetchData(url)
    }
}