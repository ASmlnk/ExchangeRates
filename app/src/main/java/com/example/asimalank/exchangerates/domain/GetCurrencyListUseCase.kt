package com.example.asimalank.exchangerates.domain

import com.example.asimalank.exchangerates.presentation.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GetCurrencyListUseCase {
    val currencyUseCase: Flow<CurrencyUseCase>
    suspend fun fetchCurrency()
}