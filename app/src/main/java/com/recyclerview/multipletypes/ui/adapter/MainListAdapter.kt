package com.recyclerview.multipletypes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recyclerview.multipletypes.databinding.ListItemBinding
import com.recyclerview.multipletypes.model.BaseItem


internal class MainListAdapter(
    private val context: Context
) : RecyclerView.Adapter<ItemViewHolder>() {

    private var listItems: ArrayList<BaseItem> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(context)
        return ItemViewHolder(ListItemBinding.inflate(inflater))
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        listItems[position].type
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with((holder)) {
            binding.listItem = listItems[position]
            binding.executePendingBindings()
        }
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

internal class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
