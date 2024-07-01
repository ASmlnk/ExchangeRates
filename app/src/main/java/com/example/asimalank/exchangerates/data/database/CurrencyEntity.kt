package com.example.asimalank.exchangerates.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asimalank.exchangerates.presentation.exchangerates.model.CurrencyModel

@Entity(tableName = "currency_cache")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "cur_id") val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "cur_abbreviation") val curAbbreviation: String,
    @ColumnInfo(name = "cur_scale") val curScale: Int,
    @ColumnInfo(name = "cur_name") val curName: String,
    @ColumnInfo(name = "cur_officialRate") val curOfficialRate: Double
) {
    fun toCurrency(): CurrencyModel {
        return CurrencyModel(
            id = this.id,
            date = this.date,
            curAbbreviation = this.curAbbreviation,
            curScale = this.curScale,
            curName = this.curName,
            curOfficialRate = this.curOfficialRate
        )
    }
}