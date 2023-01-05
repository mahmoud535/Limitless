package com.evapharma.limitless.domain.model

data class BundleData(
    val id: Int,
    val name: String,
    val products: List<Product>
)