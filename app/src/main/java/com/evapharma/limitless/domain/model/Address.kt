package com.evapharma.limitless.domain.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("Title") val addressName: String,
    @SerializedName("City") val city: String,
    @SerializedName("Area") val area: String,
    @SerializedName("Street") val street: String,
    @SerializedName("BuildingNumber") val buildingNumber: Int,
    @SerializedName("ApartmentNumber") val apartmentNumber: Int,
    @SerializedName("PhoneNumber") val mobileNumber: String,
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("Email") val email: String,
)
