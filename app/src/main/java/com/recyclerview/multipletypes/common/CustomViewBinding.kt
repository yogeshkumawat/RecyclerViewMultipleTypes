package com.recyclerview.multipletypes.common

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object CustomViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["setAdapter"])
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        this.run {
            this.adapter = adapter
        }
    }
}