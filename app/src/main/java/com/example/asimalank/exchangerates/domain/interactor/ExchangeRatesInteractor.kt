package com.example.asimalank.exchangerates.domain.interactor

import com.example.asimalank.exchangerates.domain.model.ExchangeRatesFromModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ExchangeRatesInteractor {
    suspend fun fetchCurrency()
    fun exchangeRatesFromModelStream(): StateFlow<ExchangeRatesFromModel>
}