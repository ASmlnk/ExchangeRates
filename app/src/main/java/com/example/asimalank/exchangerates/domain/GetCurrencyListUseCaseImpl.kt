package com.example.asimalank.exchangerates.domain

import com.example.asimalank.exchangerates.data.database.CurrencyLocale
import com.example.asimalank.exchangerates.data.repository.ExchangeRatesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetCurrencyListUseCaseImpl @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val currencyDateFormat: CurrencyDateFormat
) : GetCurrencyListUseCase {

    private val _isCurrencyErrorText: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    private val _isCurrencyErrorToast: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private val currencys: Flow<List<CurrencyLocale>> = exchangeRatesRepository.currency

    override val currencyUseCase =
        combine(
            _isCurrencyErrorText,
            _isCurrencyErrorToast,
            currencys
        ) { isErrorText, isErrorToast, currencys ->
            CurrencyUseCase().copy(
                currencys = currencys
                    .map { it.toCurrency() }
                    .map { it.copy(date = currencyDateFormat.convertDate(it.date)) },
                isErrorText = isErrorText,
                isErrorToast = isErrorToast
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

    private suspend fun updateCurrencyError(isError: Boolean) {
        _isCurrencyErrorText.value = isError
        _isCurrencyErrorToast.value = isError
        if (isError) {
            delay(200)
            _isCurrencyErrorToast.value = false
        }
    }
}
