package com.example.asimalank.exchangerates.presentation

import androidx.recyclerview.widget.DiffUtil

class CurrencyItemCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }
}