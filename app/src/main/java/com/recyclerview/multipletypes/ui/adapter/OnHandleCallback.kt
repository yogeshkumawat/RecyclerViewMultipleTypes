package com.recyclerview.multipletypes.ui.adapter

import com.recyclerview.multipletypes.model.BaseItem

interface OnHandleCallback {
    fun onCallback(position: Int, baseItem: BaseItem)
    fun onRemove(position: Int)
}