package com.example.asimalank.exchangerates.data.repository

import com.example.asimalank.exchangerates.data.api.CurrencyNetwork
import com.example.asimalank.exchangerates.data.database.CurrencyLocale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response
import retrofit2.http.Query

interface ExchangeRatesRepository {
    val currency: Flow<List<CurrencyLocale>>
    suspend fun fetchCurrency(onDate: String, periodicity: String): Response<List<CurrencyNetwork>>
    suspend fun insertAllCache(data: List<CurrencyLocale>)
}