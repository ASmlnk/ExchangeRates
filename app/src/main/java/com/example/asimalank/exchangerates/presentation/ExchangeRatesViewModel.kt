package com.example.asimalank.exchangerates.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asimalank.exchangerates.domain.GetCurrencyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeRatesViewModel @Inject constructor(
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ExchangeRatesUiState> =
        MutableStateFlow(ExchangeRatesUiState())
    val uiState: StateFlow<ExchangeRatesUiState>
        get() = _uiState.asStateFlow()

    init {
        fetchCurrency()

        viewModelScope.launch {
            getCurrencyListUseCase.currencyUseCase.collect { currencyUseCase ->
                _uiState.update { oldState ->
                    oldState.copy(
                        listCurrency = currencyUseCase.currencys,
                        exceptionText = currencyUseCase.isErrorText,
                        exceptionToast = currencyUseCase.isErrorToast
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
}

data class ExchangeRatesUiState(
    val listCurrency: List<Currency> = listOf(),
    val exceptionText: Boolean = false,
    val exceptionToast: Boolean = false
)