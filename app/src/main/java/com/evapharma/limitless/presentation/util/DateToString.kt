package com.evapharma.limitless.presentation.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateToString {

    fun convertToDate(strDate:String): Date =
        SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'").parse(strDate)

    fun convertToString(date: Date):String
            = SimpleDateFormat("dd MMM yyyy  hh:mm a").format(date)

}