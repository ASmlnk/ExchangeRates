package com.example.asimalank.exchangerates.domain.interactor

import com.example.asimalank.exchangerates.domain.model.ExchangeRatesFromModel
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

interface ExchangeRatesInteractor {
    suspend fun fetchCurrency(currentDate: LocalDate): StateFlow<ExchangeRatesFromModel>
}