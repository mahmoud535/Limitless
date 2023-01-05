package com.evapharma.limitless.domain.repository

interface PreferenceRepository {
    fun putString(key: String, value: String)
    fun putLong(key: String,value:Long)
    fun getLocationState(Key:String):Boolean
    fun putBoolean(key: String,value: Boolean)
    fun getString(key: String): String?
}