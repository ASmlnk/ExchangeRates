package com.example.asimalank.exchangerates.data.source.network

import com.example.asimalank.exchangerates.data.Currency
import retrofit2.Response

import retrofit2.http.GET

interface NbrbApi {
    @GET(
        "exrates/rates?ondate=2023-01-10&periodicity=0" +
                "&format=json" +
                "&nojsoncallback=0" +
                "&extras=url_s"
    )
    suspend fun fetchCurrency(): Response<List<Currency>>
}