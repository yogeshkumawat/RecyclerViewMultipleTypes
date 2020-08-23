package com.recyclerview.multipletypes.network

import com.recyclerview.multipletypes.model.ListData
import com.recyclerview.multipletypes.model.Result
import io.reactivex.Observable

interface APIService {
    fun getListData(url: String): Observable<Result<ListData>>
}