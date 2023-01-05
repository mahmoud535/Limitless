package com.evapharma.limitless.domain.model

data class CartDetails(
    val id: Int,
    var quantity: Int,
    var isMonthly: Boolean? = null,
    val cartId: Int? = null,
)
