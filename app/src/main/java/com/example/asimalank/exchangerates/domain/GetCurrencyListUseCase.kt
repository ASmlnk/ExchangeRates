package com.example.asimalank.exchangerates.domain

import com.example.asimalank.exchangerates.data.repository.ExchangeRatesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GetCurrencyListUseCase @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val exchangeRatesRepository: ExchangeRatesRepository
) {

    private val _isCurrencyError: MutableStateFlow<CurrencyError> = MutableStateFlow(CurrencyError())
    val isCurrencyError: StateFlow<CurrencyError>
        get() = _isCurrencyError.asStateFlow()

    val currencys: StateFlow<List<Currency>> = exchangeRatesRepository.currency
        .map { currencyLocale -> currencyLocale.map { it.toCurrency() } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    suspend fun fetchCurrencyNetwork() {
        try {
            val response = exchangeRatesRepository.fetchCurrency()
            if (response.isSuccessful) {
                response.body()?.let { currencyNetwork ->
                    exchangeRatesRepository.insertAllCache(currencyNetwork.map { it.toCurrencyLocale() })
                }
            }
        } catch (e: Exception) {
            _isCurrencyError.update { oldCurrencyError ->
                oldCurrencyError.copy(
                    toast = true,
                    text = true
                )
            }
        }
    }

    suspend fun fetchCurrency() {
        if (networkHelper.isNetworkAvailable()) {
            fetchCurrencyNetwork()
            _isCurrencyError.update { oldCurrencyError ->
                oldCurrencyError.copy(
                    toast = false,
                    text = false
                )
            }
        } else {
            _isCurrencyError.update { oldCurrencyError ->
                oldCurrencyError.copy(
                    toast = true,
                    text = true
                )
            }
        }
    }
}