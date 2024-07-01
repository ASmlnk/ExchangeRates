package com.example.asimalank.exchangerates.di

import com.example.asimalank.exchangerates.data.repository.ExchangeRatesRepository
import com.example.asimalank.exchangerates.domain.usecases.CurrencyDateFormatUseCase
import com.example.asimalank.exchangerates.domain.interactor.ExchangeRatesInteractor
import com.example.asimalank.exchangerates.domain.interactor.ExchangeRatesInteractorImpl
import com.example.asimalank.exchangerates.domain.usecases.NetworkAvailableUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideExchangeRatesInteractor(
        networkHelper: NetworkAvailableUseCase,
        exchangeRatesRepository: ExchangeRatesRepository,
        currencyDateFormatUseCase: CurrencyDateFormatUseCase
    ): ExchangeRatesInteractor {
        return ExchangeRatesInteractorImpl(networkHelper, exchangeRatesRepository, currencyDateFormatUseCase)
    }
}