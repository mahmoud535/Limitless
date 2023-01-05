package com.evapharma.limitless.domain.model

data class Cart(
    val products: List<CartProduct>,
    val oneTimeSubTotal: String,
    val monthlySubTotal: String,
    val discountAmount: String,
    val discountCoupon: String,
    val totalPrice: String,
)

fun Cart.mapToSummary() =
    Summary(oneTimeSubTotal, totalPrice, "0", discountAmount, products[0].currency)