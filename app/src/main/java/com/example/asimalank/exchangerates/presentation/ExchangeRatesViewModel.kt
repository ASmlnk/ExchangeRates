package com.example.asimalank.exchangerates.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asimalank.exchangerates.domain.Currency
import com.example.asimalank.exchangerates.domain.GetCurrencyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        // refreshData()
        fetchCurrency()
        viewModelScope.launch {
            getCurrencyListUseCase.currencys.collect { currency ->
                _uiState.update { oldState ->
                    oldState.copy(
                        listCurrency = currency
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
        }
}

/*fun refreshData() {
    viewModelScope.launch {
        try {
            val response = repository.fetchCurrencyNetwork()
            if (response.isSuccessful) {
                val currency = response.body() ?: emptyList()
                repository.insertAllCache(currency)
                updateStateCurrency(currency)
            }
        } catch (e: Exception) {
            val currency = repository.fetchCurrencyLocale()
            updateStateCurrency(currency)
        }
    }
}*/

fun fetchCurrency() {
    viewModelScope.launch {
        updateStateException()
        getCurrencyListUseCase.fetchCurrency()

    }
}

private fun updateStateCurrency(currency: List<Currency>) {
    val newDateCurrency = currency.map { it.copy(date = convertDate(it.date)) }
    _uiState.update { oldState ->
        oldState.copy(
            listCurrency = newDateCurrency,
            progressBar = false
        )
    }
    //if (currency.isEmpty()) updateStateException(true) else updateStateException(false)
}

private fun updateStateException() {
    _uiState.update { oldState ->
        oldState.copy(
            exceptionToast = false,
            progressBar = false
        )
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
    val progressBar: Boolean = true,
    val exceptionText: Boolean = false,
    val exceptionToast: Boolean = false
)