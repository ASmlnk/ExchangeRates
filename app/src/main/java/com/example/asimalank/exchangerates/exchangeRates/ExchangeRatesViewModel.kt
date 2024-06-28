package com.example.asimalank.exchangerates.exchangeRates

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asimalank.exchangerates.data.Currency
import com.example.asimalank.exchangerates.data.ExchangeRatesRepository
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
    private val repository: ExchangeRatesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ExchangeRatesUiState> =
        MutableStateFlow(ExchangeRatesUiState())
    val uiState: StateFlow<ExchangeRatesUiState>
        get() = _uiState.asStateFlow()

    init {
        refreshData()
    }

    private fun refreshData() {
        updateStateProgressBar()
        viewModelScope.launch {
            try {
                val response = repository.fetchCurrencyNetwork()
                if (response.isSuccessful) {
                    val currency = response.body() ?: emptyList()
                    repository.insertAllCache(currency)
                    updateStateCurrency(currency)
                    if (currency.isEmpty()) updateStateException()
                }
            } catch (e: Exception) {

                val currency = repository.fetchCurrencyLocale()
                updateStateCurrency(currency)
                if (currency.isEmpty()) updateStateException()
            }
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
    }

    private fun updateStateException() {
        _uiState.update { oldState ->
            oldState.copy(
                exception = true,
                progressBar = false
            )
        }
    }

    private fun updateStateProgressBar() {
        _uiState.update { oldState ->
            oldState.copy(
                progressBar = true
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
    val exception: Boolean = false
)