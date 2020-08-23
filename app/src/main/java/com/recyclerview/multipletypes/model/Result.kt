package com.recyclerview.multipletypes.model

data class Result<T> @JvmOverloads constructor(
    val success: Boolean,
    val data: T?,
    val exception: Exception?,
    val time: Long,
    val isFromCache: Boolean?
) {
    constructor(success: Boolean, data: T?, exception: Exception?)
            : this(success, data, exception, 0, false) {

    }
}