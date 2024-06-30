package com.example.asimalank.exchangerates.domain

import com.example.asimalank.exchangerates.data.repository.ExchangeRatesRepository
import com.example.asimalank.exchangerates.presentation.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GetCurrencyListUseCaseImpl @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val exchangeRatesRepository: ExchangeRatesRepository
) : GetCurrencyListUseCase {

    private val _isCurrencyError: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    override val isCurrencyError: StateFlow<Boolean>
        get() = _isCurrencyError.asStateFlow()

    override val currencys: StateFlow<List<Currency>> = exchangeRatesRepository.currency
        .map { currencyLocale -> currencyLocale.map { it.toCurrency() } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    override val currencyUseCase =
        combine(_isCurrencyError, exchangeRatesRepository.currency) { isError, currencys ->
            CurrencyUseCase().copy(
                currencys = currencys.map { it.toCurrency() },
                isError = isError
            )
        }.stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.Lazily,
            initialValue = CurrencyUseCase()
        )

    override suspend fun fetchCurrency() {

        if (networkHelper.isNetworkAvailable()) {
            fetchCurrencyNetwork("2023-01-25", "0")
            updateCurrencyError(false)
        } else {
            updateCurrencyError(true)

        }
        /*_isCurrencyError.update { oldCurrencyError ->
            oldCurrencyError.copy(
                toast = false,
            )
        }*/
    }

    private suspend fun fetchCurrencyNetwork(onDate: String, periodicity: String) {
        try {
            val response = exchangeRatesRepository.fetchCurrency(onDate, periodicity)
            if (response.isSuccessful) {
                response.body()?.let { currencyNetwork ->
                    exchangeRatesRepository.insertAllCache(currencyNetwork.map { it.toCurrencyLocale() })
                }
            }
        } catch (e: Exception) {
            updateCurrencyError(true)
        }
    }

    private fun updateCurrencyError(isError: Boolean) {
        _isCurrencyError.value = isError
    }
}
