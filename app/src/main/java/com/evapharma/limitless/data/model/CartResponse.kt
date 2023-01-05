package com.evapharma.limitless.data.model

import com.evapharma.limitless.domain.model.Cart

data class CartResponse(
    val data:Cart?,
    val message:String?
)