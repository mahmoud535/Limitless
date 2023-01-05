package com.evapharma.limitless.domain.model

data class Data(
    val accessToken: String,
    val birthDate: Any,
    val bmi: Double,
    val dailyStepsTarget: Int,
    val dailyWaterTarget: Int,
    val deviceToken: Any,
    val gender: Int,
    val height: Int,
    val id: Int,
    val isGuest: Boolean,
    val isVerified: Boolean,
    val language: Int,
    val os: Any,
    val osVersion: Any,
    val phone: Any,
    val profilePhoto: Any,
    val refreshToken: String,
    val username: String,
    val weekyStepsTarget: Int,
    val weight: Int
)