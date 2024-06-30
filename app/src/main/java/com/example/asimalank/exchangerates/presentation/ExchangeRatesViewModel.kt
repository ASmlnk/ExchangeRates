package com.example.asimalank.exchangerates.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asimalank.exchangerates.domain.GetCurrencyListUseCase
import com.example.asimalank.exchangerates.domain.GetCurrencyListUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ExchangeRatesViewModel @Inject constructor(
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ExchangeRatesUiState> =
        MutableStateFlow(ExchangeRatesUiState())
    val uiState: StateFlow<ExchangeRatesUiState>
        get() = _uiState.asStateFlow()

    val currency: StateFlow<List<Currency>> = getCurrencyListUseCase.currencys

    init {
        fetchCurrency()
        /*viewModelScope.launch {
            getCurrencyListUseCase.currencys.collect { currency ->
                val currencyConvertedDate = currency.map { it.copy(date = convertDate(it.date)) }
                _uiState.update { oldState ->
                    oldState.copy(
                        listCurrency = currencyConvertedDate
                    )
                }
            }
        }
        viewModelScope.launch {
            getCurrencyListUseCase.isCurrencyError.collect { exception ->
                _uiState.update { oldState ->
                    oldState.copy(
                        exceptionToast = exception.toast,
                        exceptionText = exception.text
                    )
                }
            }
        }*/
        viewModelScope.launch {
            getCurrencyListUseCase.currencyUseCase.collect { currencyUseCase ->
                _uiState.update { oldState ->
                    oldState.copy(
                        listCurrency = currencyUseCase.currencys.map { it.copy(date = convertDate(it.date)) },
                        exceptionText = currencyUseCase.isError
                    )
                }
            }
        }
    }

    fun fetchCurrency() {
        viewModelScope.launch {
            getCurrencyListUseCase.fetchCurrency()
        }
    }

    fun res(isBoolean: Boolean) {
        _uiState.update { old ->
            old.copy(exceptionText = isBoolean)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertDate(networkDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date = inputFormat.parse(networkDate)
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        return outputFormat.format(date as Date)
    }
}

data class ExchangeRatesUiState(
    val listCurrency: List<Currency> = listOf(),
    val exceptionText: Boolean = false,
    val exceptionToast: Boolean = false
)