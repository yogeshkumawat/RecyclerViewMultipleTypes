package com.recyclerview.multipletypes.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.recyclerview.multipletypes.model.BaseItem
import com.recyclerview.multipletypes.model.ListData
import com.recyclerview.multipletypes.model.Result
import io.reactivex.Observable
import java.io.InputStream

class APIServiceImpl(private val context: Context) : APIService {

    override fun getListData(url: String): Observable<Result<ListData>> {
        return getDataFromAsset()
    }

    private fun getDataFromAsset(): Observable<Result<ListData>> {
        return Observable.create {
            var response: String? = ""
            val gson = Gson()
            val inputStream: InputStream
            try {
                inputStream = context.assets.open("list_data.json")
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                response = String(buffer)
                val collectionType = object : TypeToken<ArrayList<BaseItem>>() {}.type
                val items = gson.fromJson<ArrayList<BaseItem>>(response, collectionType)
                it.onNext(Result(true, ListData(items), null))
            } catch (e: Exception) {
                e.printStackTrace()
                it.onNext(Result<ListData>(false, null, e))
            }
        }
    }
}