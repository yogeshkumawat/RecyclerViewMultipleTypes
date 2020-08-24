package com.recyclerview.multipletypes.ui.adapter

import android.widget.RadioButton
import com.recyclerview.multipletypes.databinding.SingleChoiceItemBinding
import com.recyclerview.multipletypes.model.BaseItem

class SingleChoiceViewHolder(private val binding: SingleChoiceItemBinding) :
    BaseViewHolder(binding) {

    override fun bind(position: Int, baseItem: BaseItem) {
        binding.listItem = baseItem
        binding.executePendingBindings()
        binding.radioContainer.setOnCheckedChangeListener { radioGroup, i ->
            baseItem.singleChoiceSelected = radioGroup.findViewById<RadioButton>(i).text.toString()
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