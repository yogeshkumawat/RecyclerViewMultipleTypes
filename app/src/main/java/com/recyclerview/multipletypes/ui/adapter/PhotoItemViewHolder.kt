package com.recyclerview.multipletypes.ui.adapter

import android.view.View
import com.recyclerview.multipletypes.databinding.PhotoItemBinding
import com.recyclerview.multipletypes.model.BaseItem

class PhotoItemViewHolder(private val binding: PhotoItemBinding) : BaseViewHolder(binding) {

    override fun bind(position: Int, baseItem: BaseItem) {
        binding.listItem = baseItem
        if (hasPhoto(baseItem)) {
            binding.photo.setImageBitmap(baseItem.bitmap)
            binding.closeBtn.visibility = View.VISIBLE
        }
        else {
            binding.photo.setImageBitmap(null)
            binding.closeBtn.visibility = View.GONE
        }

        binding.executePendingBindings()
        binding.photo.setOnClickListener {
            if (hasPhoto(baseItem))
                showPhoto(baseItem)
            else
                capturePhoto(position, baseItem)
        }
        binding.closeBtn.setOnClickListener {
            removePhoto(position, baseItem)
        }
    }

    private fun removePhoto(position: Int, baseItem: BaseItem) {
        baseItem.bitmap = null
        (binding.root.context as OnHandleCallback).onRemove(position)
    }

    private fun hasPhoto(baseItem: BaseItem): Boolean {
        return baseItem.bitmap != null
    }

    private fun showPhoto(baseItem: BaseItem) {
        binding.photo.setImageBitmap(baseItem.bitmap)
    }

    private fun capturePhoto(
        position: Int,
        baseItem: BaseItem
    ) {
        (binding.root.context as OnHandleCallback).onCallback(position, baseItem)
    }
}