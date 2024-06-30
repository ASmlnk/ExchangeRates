package com.example.asimalank.exchangerates.data.api

import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface NbrbApi {
    @GET(
        "exrates/rates" /*+
                "&format=json" +
                "&nojsoncallback=0" +
                "&extras=url_s"*/
    )
    suspend fun fetchCurrency(
        @Query("ondate") onDate: String,
        @Query("periodicity") periodicity: String
    ): Response<List<CurrencyNetwork>>
}