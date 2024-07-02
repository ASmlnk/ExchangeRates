package com.example.asimalank.exchangerates.data.repository

import com.example.asimalank.exchangerates.data.database.CurrencyEntity
import com.example.asimalank.exchangerates.data.database.CurrencyLocaleDao
import com.example.asimalank.exchangerates.data.api.NbrbApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRatesRepositoryImpl @Inject constructor(
    private val nbrbApi: NbrbApi,
    private val currencyLocaleDao: CurrencyLocaleDao
) : ExchangeRatesRepository {

    override fun currencyEntityStream(): Flow<List<CurrencyEntity>> = currencyLocaleDao.getAll()

    override suspend fun clearAllCache() = currencyLocaleDao.clearAll()

    override suspend fun fetchCurrency(onDate: String, periodicity: String) =
        nbrbApi.fetchCurrency(onDate, periodicity)

    override suspend fun insertAllCache(data: List<CurrencyEntity>) =
        currencyLocaleDao.insertAll(data)

    override suspend fun countCurrencyEntities() = currencyLocaleDao.countCurrencyEntities()
}

