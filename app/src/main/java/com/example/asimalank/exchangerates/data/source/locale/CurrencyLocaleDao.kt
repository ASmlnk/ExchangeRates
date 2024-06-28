package com.example.asimalank.exchangerates.data.source.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asimalank.exchangerates.data.Currency

@Dao
interface CurrencyLocaleDao {

    @Query("SELECT * FROM currency_cache")
    suspend fun getAll(): List<Currency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<Currency>)
}