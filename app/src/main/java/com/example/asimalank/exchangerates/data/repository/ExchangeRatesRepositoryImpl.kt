package com.example.asimalank.exchangerates.data.repository

import com.example.asimalank.exchangerates.data.database.CurrencyLocale
import com.example.asimalank.exchangerates.data.database.CurrencyLocaleDao
import com.example.asimalank.exchangerates.data.api.NbrbApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRatesRepositoryImpl @Inject constructor(
    private val nbrbApi: NbrbApi,
    private val currencyLocaleDao: CurrencyLocaleDao
) : ExchangeRatesRepository {

    override val currency: Flow<List<CurrencyLocale>> = currencyLocaleDao.getAll()
    override suspend fun fetchCurrency(onDate: String, periodicity: String) =
        nbrbApi.fetchCurrency(onDate, periodicity)

    override suspend fun insertAllCache(data: List<CurrencyLocale>) =
        currencyLocaleDao.insertAll(data)

}

