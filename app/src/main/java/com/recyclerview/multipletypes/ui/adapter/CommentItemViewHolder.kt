package com.recyclerview.multipletypes.ui.adapter

import android.view.View
import android.widget.CompoundButton
import com.recyclerview.multipletypes.databinding.CommentItemBinding
import com.recyclerview.multipletypes.model.BaseItem

class CommentItemViewHolder(private val binding: CommentItemBinding) : BaseViewHolder(binding) {

    override fun bind(position: Int, baseItem: BaseItem) {
        binding.listItem = baseItem
        binding.executePendingBindings()
        binding.commentSwitch.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
            if(checked)
                showCommentBox()
            else
                hideCommentBox()
        }
    }

    private fun showCommentBox() {
        binding.commentBox.visibility = View.VISIBLE
    }

    private fun hideCommentBox() {
        binding.commentBox.visibility = View.GONE
    }
}