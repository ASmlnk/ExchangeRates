package com.example.asimalank.exchangerates.data.source.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.asimalank.exchangerates.data.Currency

@Database(entities = [Currency::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun currencyLocaleDao(): CurrencyLocaleDao
}