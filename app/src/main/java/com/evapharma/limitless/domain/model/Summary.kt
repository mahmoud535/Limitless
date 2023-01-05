package com.evapharma.limitless.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Summary(
    val subTotal: String,
    val total: String,
    val deliveryFee: String,
    val promoCode: String,
    val currency:String
) : Parcelable