package com.evapharma.limitless.domain.model

import java.util.*

data class OrderHistory(
    val id: Int,
    val orderNumber:Int,
    val totalPrice:String,
    val orderStatus:String,
    val orderStatusInt:Int,
    val date:String,
    val currency: String,
    )
