package com.example.asimalank.exchangerates.domain

import com.example.asimalank.exchangerates.data.repository.ExchangeRatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class CurrencyUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideCurrencyUseCaseModule(
        networkHelper: NetworkHelper,
        exchangeRatesRepository: ExchangeRatesRepository
    ): GetCurrencyListUseCase {
        return GetCurrencyListUseCaseImpl(networkHelper, exchangeRatesRepository)
    }
}