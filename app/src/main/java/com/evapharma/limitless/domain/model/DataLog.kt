package com.evapharma.limitless.domain.model

data class DataLog(
    val data: LoginModel?,
    val errorList: List<Any>?,
    val message: String?
)