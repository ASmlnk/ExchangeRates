package com.example.asimalank.exchangerates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyLocale::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun currencyLocaleDao(): CurrencyLocaleDao
}