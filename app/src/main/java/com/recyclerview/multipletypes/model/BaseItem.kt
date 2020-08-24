package com.recyclerview.multipletypes.model

import android.graphics.Bitmap

data class BaseItem(
    val type: String, val id: String,
    val title: String,
    val dataMap: DataMap,
    var bitmap: Bitmap?,
    var singleChoiceSelected: String,
    var provideComment: Boolean,
    var comment: String
)