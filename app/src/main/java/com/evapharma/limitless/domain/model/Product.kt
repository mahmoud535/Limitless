package com.evapharma.limitless.domain.model

import android.os.Parcelable
import java.io.Serializable

data class Product(
    val id: Int,
    val imageUrl: String?,
    val name: String,
    val fullDescription: String,
    val shortDescription: String,
    val price: String,
    val priceValue: Double,
    val oldPrice: String?,
    val discountPercentage: Int,
    var isMonthlySubscription: Boolean,
    val currency: String,
    val addToCart: CartDetails,
    val hasDiscount: Boolean,
    val productManufacturers: List<ProductManufacturer>,
    val productTags: List<ProductTag>,
    val attributes: List<ProductAttribute>

): Serializable