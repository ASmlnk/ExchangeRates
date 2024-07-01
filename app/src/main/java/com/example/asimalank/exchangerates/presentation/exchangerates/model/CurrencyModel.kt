package com.example.asimalank.exchangerates.presentation.exchangerates.model

data class CurrencyModel(
    val id: Int,
    val date: String,
    val curAbbreviation: String,
    val curScale: Int,
    val curName: String,
    val curOfficialRate: Double
)


