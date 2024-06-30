package com.example.asimalank.exchangerates.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyLocaleDao {

    @Query("SELECT * FROM currency_cache")
    fun getAll(): Flow<List<CurrencyLocale>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CurrencyLocale>)
}