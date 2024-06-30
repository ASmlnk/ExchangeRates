package com.example.asimalank.exchangerates.domain

import com.example.asimalank.exchangerates.presentation.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GetCurrencyListUseCase {
    val isCurrencyError: StateFlow<Boolean>
    val currencys: StateFlow<List<Currency>>
    val currencyUseCase: Flow<CurrencyUseCase>
    suspend fun fetchCurrency()
}