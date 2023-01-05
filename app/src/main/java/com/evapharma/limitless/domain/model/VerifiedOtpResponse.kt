package com.evapharma.limitless.domain.model

data class VerifiedOtpResponse(
    val `data`: Data,
    val errorList: List<Any>,
    val message: String
)