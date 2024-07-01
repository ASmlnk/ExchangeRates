package com.example.asimalank.exchangerates.domain

import com.example.asimalank.exchangerates.presentation.Currency

data class CurrencyUseCase(
    val currencys: List<Currency> = emptyList(),
    val isErrorText: Boolean = false,
    val isErrorToast: Boolean = false
)
