package com.recyclerview.multipletypes.ui.adapter

import com.recyclerview.multipletypes.databinding.PhotoItemBinding
import com.recyclerview.multipletypes.model.BaseItem

class PhotoItemViewHolder(private val binding: PhotoItemBinding) : BaseViewHolder(binding) {

    override fun bind(baseItem: BaseItem) {
        binding.listItem = baseItem
        binding.executePendingBindings()
    }
}