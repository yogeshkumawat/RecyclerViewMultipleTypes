package com.recyclerview.multipletypes

import com.recyclerview.multipletypes.model.ListData
import com.recyclerview.multipletypes.model.Result
import com.recyclerview.multipletypes.network.APIHelper
import io.reactivex.Observable

class MainRepository(private val apiHelper: APIHelper) {
    var localListData: ListData? = null

    fun fetchData(url: String): Observable<Result<ListData>> {
        return if (localListData != null)
            Observable.just(Result(true, localListData, null))
        else
            fetchFromNetwork(url)
    }

    private fun fetchFromNetwork(url: String): Observable<Result<ListData>> {
        return apiHelper.getListData(url).map {
            localListData = it.data
            it
        }
    }

}