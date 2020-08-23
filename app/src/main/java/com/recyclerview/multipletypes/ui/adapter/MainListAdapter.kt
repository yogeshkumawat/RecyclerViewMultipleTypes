package com.recyclerview.multipletypes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recyclerview.multipletypes.databinding.CommentItemBinding
import com.recyclerview.multipletypes.databinding.PhotoItemBinding
import com.recyclerview.multipletypes.databinding.SingleChoiceItemBinding
import com.recyclerview.multipletypes.model.BaseItem


internal class MainListAdapter(
    private val context: Context
) : RecyclerView.Adapter<BaseViewHolder>() {

    var listItems: ArrayList<BaseItem> = ArrayList()

    companion object {
        const val ITEM_TYPE_PHOTO = 1
        const val ITEM_TYPE_SINGLE_CHOICE = 2
        const val ITEM_TYPE_COMMENT = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            ITEM_TYPE_PHOTO -> PhotoItemViewHolder(PhotoItemBinding.inflate(inflater, parent, false))
            ITEM_TYPE_SINGLE_CHOICE -> SingleChoiceViewHolder(
                SingleChoiceItemBinding.inflate(inflater, parent, false)
            )
            ITEM_TYPE_COMMENT -> CommentItemViewHolder(CommentItemBinding.inflate(inflater, parent, false))
            else -> PhotoItemViewHolder(PhotoItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listItems[position].type) {
            "PHOTO" -> ITEM_TYPE_PHOTO
            "SINGLE_CHOICE" -> ITEM_TYPE_SINGLE_CHOICE
            "COMMENT" -> ITEM_TYPE_COMMENT
            else -> ITEM_TYPE_PHOTO
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(position, listItems[position])
    }

    fun addListItems(data: ArrayList<BaseItem>) {
        for (listItem in data)
            addItem(listItem)
    }

    private fun addItem(item: BaseItem) {
        listItems.add(item)
        notifyItemInserted(listItems.size - 1)
    }
}
