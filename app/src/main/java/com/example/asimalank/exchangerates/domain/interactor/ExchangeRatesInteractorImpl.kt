package com.example.asimalank.exchangerates.domain.interactor

import com.example.asimalank.exchangerates.data.database.CurrencyEntity
import com.example.asimalank.exchangerates.data.repository.ExchangeRatesRepository
import com.example.asimalank.exchangerates.domain.usecases.CurrencyDateFormatUseCase
import com.example.asimalank.exchangerates.domain.model.ExchangeRatesFromModel
import com.example.asimalank.exchangerates.domain.usecases.DateRequestApiUseCase
import com.example.asimalank.exchangerates.domain.usecases.NetworkAvailableUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject

class ExchangeRatesInteractorImpl @Inject constructor(
    private val networkHelper: NetworkAvailableUseCase,
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val currencyDateFormatUseCase: CurrencyDateFormatUseCase,
    private val dateRequestApiUseCase: DateRequestApiUseCase
) : ExchangeRatesInteractor {

    private val _isCurrencyErrorViewText: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    private val _isCurrencyErrorViewToast: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private val currencyEntity: Flow<List<CurrencyEntity>> =
        exchangeRatesRepository.currencyEntityStream()

    private val exchangeRatesFromModelStream =
        combine(
            _isCurrencyErrorViewText,
            _isCurrencyErrorViewToast,
            currencyEntity
        ) { isErrorViewText, isErrorViewToast, currencyEntity ->
            ExchangeRatesFromModel().copy(
                currencys = currencyEntity
                    .map { it.toCurrency() }
                    .map { currency ->
                        currency.copy(
                            date = currencyDateFormatUseCase.convertDate(
                                currency.date
                            )
                        )
                    },
                isErrorViewText = isErrorViewText,
                isErrorViewToast = isErrorViewToast
            )
        }.stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.Lazily,
            initialValue = ExchangeRatesFromModel()
        )

    override suspend fun fetchCurrency(currentDate: LocalDate): StateFlow<ExchangeRatesFromModel> {
        val dateRequest = dateRequestApiUseCase.localDateToDateRequestApi(currentDate)
        if (networkHelper.isNetworkAvailable()) {
            fetchCurrencyNetwork(dateRequest, "0")
            updateCurrencyError(false)
        } else {
            updateCurrencyError(true)
        }
        return exchangeRatesFromModelStream
    }

    private suspend fun fetchCurrencyNetwork(onDate: String, periodicity: String) {
        try {
            val response = exchangeRatesRepository.fetchCurrency(onDate, periodicity)
            if (response.isSuccessful) {
                response.body()?.let { currencyNetwork ->
                    exchangeRatesRepository.insertAllCache(currencyNetwork.map { it.toCurrencyEntity() })
                }
            }
        } catch (e: Exception) {
            updateCurrencyError(true)
        }
    }

    private suspend fun updateCurrencyError(isError: Boolean) {
        _isCurrencyErrorViewText.value = isError
        _isCurrencyErrorViewToast.value = isError
        if (isError) {
            delay(200)
            _isCurrencyErrorViewToast.value = false
        }
    }
}
