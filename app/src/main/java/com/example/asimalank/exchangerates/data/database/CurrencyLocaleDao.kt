package com.example.asimalank.exchangerates.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyLocaleDao {

    @Query("SELECT * FROM currency_cache")
    fun getAll(): Flow<List<CurrencyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CurrencyEntity>)

    @Query("DELETE FROM currency_cache")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM currency_cache")
    suspend fun countCurrencyEntities(): Int
}