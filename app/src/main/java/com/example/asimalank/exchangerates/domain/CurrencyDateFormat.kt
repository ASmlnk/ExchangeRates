package com.example.asimalank.exchangerates.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CurrencyDateFormat @Inject constructor() {
    fun convertDate(networkDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date = inputFormat.parse(networkDate)
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        return outputFormat.format(date as Date)
    }
}