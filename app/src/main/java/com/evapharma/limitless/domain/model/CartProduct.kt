package com.evapharma.limitless.domain.model

data class CartProduct(
    val id: Int,
    val shoppingCartItemId:Int,
    val imageUrl: String,
    val name: String,
    val shortDescription: String,
    val currentPrice:Int,
    var quantity:Int,
    val oldPrice:Int?,
    var isMonthly:Boolean,
    val unitPrice:String,
    val subTotalPrice:String,
    val currency:String,
)
