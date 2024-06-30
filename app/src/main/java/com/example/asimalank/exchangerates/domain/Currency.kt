package com.example.asimalank.exchangerates.domain

data class Currency(
    val id: Int,
    val date: String,
    val curAbbreviation: String,
    val curScale: Int,
    val curName: String,
    val curOfficialRate: Double
)


