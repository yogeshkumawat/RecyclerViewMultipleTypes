package com.recyclerview.multipletypes.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.recyclerview.multipletypes.model.BaseItem

abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(baseItem: BaseItem)
}