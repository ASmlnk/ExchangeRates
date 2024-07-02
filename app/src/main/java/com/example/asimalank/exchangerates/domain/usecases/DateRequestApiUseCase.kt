package com.example.asimalank.exchangerates.domain.usecases

import java.time.LocalDate
import javax.inject.Inject

class DateRequestApiUseCase @Inject constructor() {

    fun localDateToDateRequestApi(localDate: LocalDate): String {
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth
        return "$year-$month-$day"
    }
}