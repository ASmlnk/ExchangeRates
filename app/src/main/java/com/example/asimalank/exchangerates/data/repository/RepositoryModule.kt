package com.example.asimalank.exchangerates.data.repository

import com.example.asimalank.exchangerates.data.api.NbrbApi
import com.example.asimalank.exchangerates.data.database.CurrencyLocaleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideExchangeRatesRepository(
        nbrbApi: NbrbApi,
        currencyLocaleDao: CurrencyLocaleDao
    ): ExchangeRatesRepository {
        return ExchangeRatesRepositoryImpl(nbrbApi, currencyLocaleDao)
    }
}