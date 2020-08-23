package com.recyclerview.multipletypes.ui.adapter

import android.util.Log
import android.widget.RadioButton
import com.recyclerview.multipletypes.databinding.SingleChoiceItemBinding
import com.recyclerview.multipletypes.model.BaseItem

class SingleChoiceViewHolder(private val binding: SingleChoiceItemBinding) :
    BaseViewHolder(binding) {

    override fun bind(baseItem: BaseItem) {
        binding.listItem = baseItem
        binding.executePendingBindings()
        binding.radioContainer.setOnCheckedChangeListener { _, i ->
            Log.v("BOSS2", "Checked $i")
        }
        addRadioButtons(baseItem)
    }

    private fun addRadioButtons(baseItem: BaseItem) {
        if(binding.radioContainer.childCount == 0) baseItem.dataMap.options.forEach { option ->
            val radioButton = RadioButton(binding.root.context)
            radioButton.text = option
            binding.radioContainer.addView(radioButton)
        }
    }
}