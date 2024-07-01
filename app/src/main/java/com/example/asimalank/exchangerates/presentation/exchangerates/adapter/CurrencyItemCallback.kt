package com.example.asimalank.exchangerates.presentation.exchangerates.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.asimalank.exchangerates.presentation.exchangerates.model.CurrencyModel

class CurrencyItemCallback : DiffUtil.ItemCallback<CurrencyModel>() {
    override fun areContentsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem == newItem
    }
}