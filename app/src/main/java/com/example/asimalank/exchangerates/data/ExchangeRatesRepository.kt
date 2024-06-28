package com.example.asimalank.exchangerates.data

import com.example.asimalank.exchangerates.data.source.locale.CurrencyLocaleDao
import com.example.asimalank.exchangerates.data.source.network.NbrbApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRatesRepository @Inject constructor(
    private val nbrbApi: NbrbApi,
    private val currencyLocaleDao: CurrencyLocaleDao
) {

    suspend fun fetchCurrencyLocale() = currencyLocaleDao.getAll()
    suspend fun fetchCurrencyNetwork() = nbrbApi.fetchCurrency()
    suspend fun insertAllCache(data: List<Currency>) = currencyLocaleDao.insertAll(data)
}