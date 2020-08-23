package com.recyclerview.multipletypes.network

class APIHelper(private val apiService: APIService) {
    fun getListData(url: String) = apiService.getListData(url)
}