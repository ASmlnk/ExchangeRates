package com.example.asimalank.exchangerates.data.repository

import com.example.asimalank.exchangerates.data.api.CurrencyDto
import com.example.asimalank.exchangerates.data.database.CurrencyEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ExchangeRatesRepository {
    suspend fun fetchCurrency(onDate: String, periodicity: String): Response<List<CurrencyDto>>
    suspend fun insertAllCache(data: List<CurrencyEntity>)
    fun currencyEntityStream(): Flow<List<CurrencyEntity>>
}