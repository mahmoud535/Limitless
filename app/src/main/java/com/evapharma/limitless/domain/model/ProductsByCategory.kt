package com.evapharma.limitless.domain.model


data class ProductsByCategory (
    val categoryName: String,
    val products:List<Product>,
)