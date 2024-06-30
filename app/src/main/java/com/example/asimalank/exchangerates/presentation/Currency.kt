package com.example.asimalank.exchangerates.presentation

data class Currency(
    val id: Int,
    val date: String,
    val curAbbreviation: String,
    val curScale: Int,
    val curName: String,
    val curOfficialRate: Double
)


