package com.evapharma.limitless.domain.model

import com.google.gson.annotations.SerializedName

data class AddressWithoutPhoneNumber(
    @SerializedName("title") val addressName: String,
    @SerializedName("city") val city: String,
    @SerializedName("area") val area: String,
    @SerializedName("street") val street: String,
    @SerializedName("buildingNumber") val buildingNumber: Int,
    @SerializedName("apartmentNumber") val apartmentNumber: Int,
    @SerializedName("id") val id: Int,

    var isDefault: Boolean? = null

)