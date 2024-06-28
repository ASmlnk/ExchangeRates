package com.example.asimalank.exchangerates.data.source.locale

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocaleModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideCurrencyLocaleDao(dataBase: AppDataBase): CurrencyLocaleDao {
        return dataBase.currencyLocaleDao()
    }
}