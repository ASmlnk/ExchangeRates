package com.example.asimalank.exchangerates.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asimalank.exchangerates.R
import com.example.asimalank.exchangerates.domain.Currency
import com.example.asimalank.exchangerates.databinding.ItemCurrencyBinding
import javax.inject.Inject

class CurrencyAdapter @Inject constructor(): ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(
    CurrencyItemCallback()
) {

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder.inflateFrom(parent)

    class CurrencyViewHolder (private val binding: ItemCurrencyBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): CurrencyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCurrencyBinding.inflate(layoutInflater, parent, false)
                return CurrencyViewHolder(binding)
            }
        }

        fun bind (item: Currency) {
            binding.apply {
                curName.text = item.curName
                date.text = item.date
                curScale.text = item.curScale.toString()
                curAbbreviation.text = item.curAbbreviation
                curOfficialRate.text = itemView.context.getString(R.string.cur_officialRate, item.curOfficialRate.toString())
            }
        }
    }
}