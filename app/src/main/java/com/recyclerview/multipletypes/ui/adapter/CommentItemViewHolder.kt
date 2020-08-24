package com.recyclerview.multipletypes.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import com.recyclerview.multipletypes.databinding.CommentItemBinding
import com.recyclerview.multipletypes.model.BaseItem

class CommentItemViewHolder(private val binding: CommentItemBinding) : BaseViewHolder(binding) {

    override fun bind(position: Int, baseItem: BaseItem) {
        binding.listItem = baseItem
        binding.executePendingBindings()
        binding.commentSwitch.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
            if(checked) {
                showCommentBox()
            }
            else {
                hideCommentBox()
                baseItem.comment = ""
            }
            baseItem.provideComment = checked

        }
        binding.commentBox.addTextChangedListener(MyTextChange(baseItem))
    }

    private fun showCommentBox() {
        binding.commentBox.visibility = View.VISIBLE
    }

    private fun hideCommentBox() {
        binding.commentBox.visibility = View.GONE
    }

    internal class MyTextChange(val baseItem: BaseItem) : TextWatcher {
        override fun afterTextChanged(edit: Editable?) {
            if(edit != null && edit.isNotEmpty())
                baseItem.comment = edit.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    }
}