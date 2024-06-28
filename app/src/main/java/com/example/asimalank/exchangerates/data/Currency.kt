package com.example.asimalank.exchangerates.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "currency_cache")
@JsonClass(generateAdapter = true)
data class Currency(
    @PrimaryKey
    @ColumnInfo(name = "cur_id") @Json(name = "Cur_ID") val id: Int,
    @ColumnInfo(name = "date") @Json(name = "Date")val date: String,
    @ColumnInfo(name = "cur_abbreviation") @Json(name = "Cur_Abbreviation")val curAbbreviation: String,
    @ColumnInfo(name = "cur_scale") @Json(name = "Cur_Scale")val curScale: Int,
    @ColumnInfo(name = "cur_name") @Json(name = "Cur_Name")val curName: String,
    @ColumnInfo(name = "cur_officialRate") @Json(name = "Cur_OfficialRate")val curOfficialRate: Double
)


