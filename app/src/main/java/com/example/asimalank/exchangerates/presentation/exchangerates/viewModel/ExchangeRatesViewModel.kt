package com.example.asimalank.exchangerates.presentation.exchangerates.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asimalank.exchangerates.domain.interactor.ExchangeRatesInteractor
import com.example.asimalank.exchangerates.presentation.exchangerates.model.ExchangeRatesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeRatesViewModel @Inject constructor(
    private val exchangeRatesInteractor: ExchangeRatesInteractor
) : ViewModel() {

    private val _uiState: MutableStateFlow<ExchangeRatesUiState> =
        MutableStateFlow(ExchangeRatesUiState())
    val uiState: StateFlow<ExchangeRatesUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            exchangeRatesInteractor.fetchCurrency()
                .collect { exchangeRatesFromModel ->
                    _uiState.update { oldState ->
                        oldState.copy(
                            listCurrency = exchangeRatesFromModel.currencys,
                            isErrorViewText = exchangeRatesFromModel.isErrorViewText,
                            isErrorViewToast = exchangeRatesFromModel.isErrorViewToast
                        )
                    }
                }
        }
    }

    fun fetchCurrency() {
        viewModelScope.launch {
            exchangeRatesInteractor.fetchCurrency()
        }
    }
}