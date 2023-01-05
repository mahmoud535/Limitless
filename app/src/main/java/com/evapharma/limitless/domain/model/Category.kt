package com.evapharma.limitless.domain.model

data class Category(
    val id:Int,
    val name:String,
    val products: List<Product>,
    @Transient
    var image_drawable:Int
)