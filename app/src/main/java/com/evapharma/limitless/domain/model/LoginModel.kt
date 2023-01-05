package com.evapharma.limitless.domain.model

data class LoginModel(
    val phone: String,
    var isGuest: Boolean,
    val accessToken: String,
//    val deviceToken: String,
//    val language: Int,
//    val os: String,
//    val osVersion: String,
    )
