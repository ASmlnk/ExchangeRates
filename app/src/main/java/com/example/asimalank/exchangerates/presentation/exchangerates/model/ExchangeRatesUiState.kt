package com.example.asimalank.exchangerates.presentation.exchangerates.model

data class ExchangeRatesUiState(
    val listCurrency: List<CurrencyModel> = listOf(),
    val isErrorViewText: Boolean = false,
    val isErrorViewToast: Boolean = false
)
