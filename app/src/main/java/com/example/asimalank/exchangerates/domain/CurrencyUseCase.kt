package com.example.asimalank.exchangerates.domain

import com.example.asimalank.exchangerates.presentation.Currency

data class CurrencyUseCase(
    val currencys: List<Currency> = emptyList(),
    val isError: Boolean = false
)
