package com.example.asimalank.exchangerates.data.api

import com.example.asimalank.exchangerates.data.database.CurrencyEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyDto(
    @Json(name = "Cur_ID") val id: Int,
    @Json(name = "Date") val date: String,
    @Json(name = "Cur_Abbreviation") val curAbbreviation: String,
    @Json(name = "Cur_Scale") val curScale: Int,
    @Json(name = "Cur_Name") val curName: String,
    @Json(name = "Cur_OfficialRate") val curOfficialRate: Double
) {
    fun toCurrencyEntity(): CurrencyEntity {
        return CurrencyEntity(
            id = this.id,
            date = this.date,
            curAbbreviation = this.curAbbreviation,
            curScale = this.curScale,
            curName = this.curName,
            curOfficialRate = this.curOfficialRate
        )
    }
}