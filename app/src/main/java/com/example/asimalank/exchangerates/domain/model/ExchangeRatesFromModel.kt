package com.example.asimalank.exchangerates.domain.model

import com.example.asimalank.exchangerates.presentation.exchangerates.model.CurrencyModel

data class ExchangeRatesFromModel(
    val currencys: List<CurrencyModel> = emptyList(),
    val isErrorViewText: Boolean = false,
    val isErrorViewToast: Boolean = false
)
